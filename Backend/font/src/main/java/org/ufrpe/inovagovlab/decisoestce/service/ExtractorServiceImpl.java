package org.ufrpe.inovagovlab.decisoestce.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.http.client.utils.URIBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.ufrpe.inovagovlab.decisoestce.mapper.LimiteDeGastosMapper;
import org.ufrpe.inovagovlab.decisoestce.mapper.ProcessoMapper;
import org.ufrpe.inovagovlab.decisoestce.model.*;
import org.ufrpe.inovagovlab.decisoestce.repository.*;
import org.ufrpe.inovagovlab.decisoestce.util.DelimitersDetRec;
import org.ufrpe.inovagovlab.decisoestce.util.HtmlUtils;

import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class ExtractorServiceImpl{

    @Autowired
    private ProcessoMapper processoMapper;

    @Autowired
    private ProcessoRepository processoRepository;
    @Autowired
    private ConsiderandoRepository considerandoRepository;
    @Autowired
    private DeterminacaoRepository determinacaoRepository;
    @Autowired
    private RecomendacaoRepository recomendacaoRepository;
    @Autowired
    private LimiteDeGastosRepository limiteDeGastosRepository;

    @Value("${delim.path}")
    private String delimPath;
    private static final Logger logger = LoggerFactory.getLogger(ExtractorServiceImpl.class);

    /**
     * Coleta os processos transitados em julgado
     * Documentação do Endpoint: https://sistemas.tce.pe.gov.br/DadosAbertos/Exemplo!detalhar?dadosAbertos.id=42
     * Endpoint: https://sistemas.tce.pe.gov.br/DadosAbertos/Resultados!json
     * Em 26/03/2022 retornava 4430 resultados
     * TODO Esse método processa 3010. A diferença deve ser por chave duplicada (ele faz um merge). Tem que checar isso
     *
     * ID de teste: 15100001-3
     * Consulta de 1 específico: https://sistemas.tce.pe.gov.br/DadosAbertos/Resultados!json?Processo=15100001-3
     *
     * Instruções para acessar uma API restful:
     * 1. Sync: https://dzone.com/articles/3-popular-ways-to-consume-with-rest-apis-in-java
     * 2. Async: https://dzone.com/articles/java-11-http-client-api-to-consume-restful-web-ser-1
     */

    public void collectProcessos() throws ExecutionException, InterruptedException {

            String endpoint = "https://sistemas.tce.pe.gov.br/DadosAbertos/Resultados!json";
            //String endpoint = "https://sistemas.tce.pe.gov.br/DadosAbertos/Resultados!json?Processo=15100001-3";

            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest httpRequest = HttpRequest.newBuilder(URI.create(endpoint))
                    .header("Content-Type", "application/json").GET().build();

            CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.get().statusCode() != 200){
                //TODO Tratar o erro
            } else {
                int noArray = 0;
                int naLista = 0;
                int salvos = 0;
                //success
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);

                JsonNode jsonNode = null;

                List<Processo> processos = new ArrayList<Processo>();
                try {
                    jsonNode = mapper.readTree(response.get().body()).findPath("conteudo");
                    //processoDTOList = mapper.readValue(jsonNode.asText(), new TypeReference<List<ProcessoDTO>>(){});

                    if (jsonNode.isArray()){
                        //System.out.println("+++++ Processos no array: " + jsonNode.size());
                        noArray =  jsonNode.size();
                        ArrayNode arrayNode = (ArrayNode)jsonNode;
                        for(int i = 0; i < arrayNode.size(); i++) {
                            JsonNode n = arrayNode.get(i);
                            Processo p = processoMapper.toEntity(n);
                            processos.add(p);
                        }
                    }

                    //Salvar
                    System.out.println("+++++ Processos na lista: " + processos.size());
                    naLista = processos.size();

                    for (Processo p: processos){
                        processoRepository.save(p);
                        salvos++;
                    }

                    logger.info("+++++ Processos no array: " + noArray + " / Na lista: " + naLista + " / Salvos: " + salvos);
                    System.out.println("+++++ Processos no array: " + noArray + " / Na lista: " + naLista + " / Salvos: " + salvos);

                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
    }

    /**
     * Coleta os considerandos dos processos transitados em julgado
     * Documentação do Endpoint: https://sistemas.tce.pe.gov.br/DadosAbertos/Exemplo!detalhar?dadosAbertos.id=62
     * Endpoint: https://sistemas.tce.pe.gov.br/DadosAbertos/Considerandos!json
     * Em 26/03/2022 retornava 17478 resultados
     *
     * Ao invés de buscar tudo, faço diversas chamadas, uma para cada ID que já temos na base
     * Já lê os vários considerandos e converte em um só
     * Checa se tem mais de uma pessoa e se tem mais de um tipo em sets. Poderia fazer as pessoas jogando em uma tabela
     * nova, mas referi deixar assim mais simples.
     *
     * ID de teste: 15100001-3
     * Consulta de 1 específico: https://sistemas.tce.pe.gov.br/DadosAbertos/Considerandos!json?Processo=15100001-3
     */

    public void collectConsiderandos() throws ExecutionException, InterruptedException {

        List<Processo> processos = processoRepository.findAll();

        String endpoint = "https://sistemas.tce.pe.gov.br/DadosAbertos/Considerandos!json";

        HttpClient httpClient = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);

        int processosCount = 0;
        int salvosCount = 0;

        for (Processo p: processos){
            processosCount = processos.size();
            try {

                /*  //For debug
                if(p.getId().equals("19100416-9")){
                    System.out.println("Pare");
                } else { continue; }*/

                URI uri = new URIBuilder(endpoint)
                        .addParameter("Processo", p.getId())
                        .build();
                HttpRequest httpRequest = HttpRequest.newBuilder(uri).GET().build();
                CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());

                //Processa a resposta
                JsonNode jsonNode = mapper.readTree(response.get().body()).findPath("conteudo");
                Set<String> tiposSet = new HashSet<String>();
                Set<String> pessoasSet = new HashSet<String>();
                ArrayList<String> conteudosList = new ArrayList<String>(jsonNode.size());
                ArrayList<String> limposList = new ArrayList<String>(jsonNode.size());

                for(int i = 0; i < jsonNode.size(); i++) {
                    JsonNode node = jsonNode.get(i);
                    String id = node.get("Processo").asText().trim();

                    if (!id.equals(p.getId())){
                        //logger.error("Erro ao coletar os considerandos do processo. ID diferente do solicitado: ID = " + id + ", solicitado = " + p.getId());
                        continue;
                    }

                    String tipo = node.get("TipoConsiderando").asText().trim();
                    String pessoa = "";
                    try { pessoa = node.get("NomePessoaConsiderando").asText().trim(); } catch (Exception e){}
                    String conteudo = node.get("Conteudo").asText().trim();

                    Document doc = Jsoup.parse(conteudo);
                    Elements htmlId = doc.getElementsByTag("IdProcessoConsiderandoITD");
                    String codigo = htmlId.text(); //Considerando's ID. Do nothing

                    Elements htmlContent = doc.getElementsByTag("p");
                    String limpo = htmlContent.text();  //Considerando's content
                    limpo = HtmlUtils.cleanAccentuation(limpo);

                    limpo = limpo.replaceAll("Considerando", "##### Considerando");
                    limpo = limpo.replaceAll("considerando", "##### Considerando");
                    limpo = limpo.replaceAll("CONSIDERANDO", "##### Considerando");

                    if (!limpo.isEmpty() && limpo.charAt(0) == '#'){
                        limpo = limpo.substring(6);
                    }

                    if (!tipo.isEmpty())
                        tiposSet.add(tipo); //não repete
                    if (!pessoa.isEmpty())
                        pessoasSet.add(pessoa);
                    conteudosList.add(conteudo); // repete
                    limposList.add(limpo);
                }

                //When finished, group everything together
                String tipos = tiposSet.stream().map(String::valueOf).collect(Collectors.joining(", "));
                String pessoas = pessoasSet.stream().map(String::valueOf).collect(Collectors.joining(", "));
                String conteudos = conteudosList.stream().map(String::valueOf).collect(Collectors.joining(" "));
                String limpos = limposList.stream().map(String::valueOf).collect(Collectors.joining("#####"));


                Considerando c = new Considerando();
                c.setIdProcesso(p.getId());
                c.setTipoConsiderando(tipos);
                c.setPessoaConsiderando(pessoas);
                c.setTextoOriginal(conteudos);
                c.setTextoLimpo(limpos);

                try {
                    considerandoRepository.save(c);
                } catch (Exception e){
                    logger.error("Erro salvando " + c, e);
                    throw new Exception(e);
                }
                salvosCount++;
                if (salvosCount % 50 == 0){
                    logger.info("Salvando considerandos. Salvos = " + salvosCount + " - " + c.getIdProcesso() + " - " + LocalDateTime.now());
                }

            } catch (URISyntaxException e) {
                //e.printStackTrace();
                logger.error("Erro ao coletar os considerandos do processo " + p.getId(), e);
            } catch (JsonMappingException e) {
                logger.error("Erro ao processar o JSON  do processo " + p.getId(), e);
            } catch (JsonProcessingException e) {
                logger.error("Erro ao processar o JSON  do processo " + p.getId(), e);
            } catch (Exception e) {
                logger.error("Erro ao processar o processo " + p.getId(), e);
            } finally {
            }
        }
        logger.info("Processos: " + processosCount + ". Salvos: " + salvosCount);
    }

    // Sobrecarga do método para que o programa só vá a Api buscar os processos necessários

    public void collectConsiderandos(List<Processo> novosProcessos) throws ExecutionException, InterruptedException {

        List<Processo> processos = novosProcessos;

        String endpoint = "https://sistemas.tce.pe.gov.br/DadosAbertos/Considerandos!json";

        HttpClient httpClient = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);

        int processosCount = 0;
        int salvosCount = 0;

        for (Processo p: processos){
            processosCount = processos.size();
            try {

                /*  //For debug
                if(p.getId().equals("15100300-2")){
                    System.out.println("Pare");
                } else { continue; } */

                URI uri = new URIBuilder(endpoint)
                        .addParameter("Processo", p.getId())
                        .build();
                HttpRequest httpRequest = HttpRequest.newBuilder(uri).GET().build();
                CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());

                //Processa a resposta
                JsonNode jsonNode = mapper.readTree(response.get().body()).findPath("conteudo");
                Set<String> tiposSet = new HashSet<String>();
                Set<String> pessoasSet = new HashSet<String>();
                ArrayList<String> conteudosList = new ArrayList<String>(jsonNode.size());
                ArrayList<String> limposList = new ArrayList<String>(jsonNode.size());

                for(int i = 0; i < jsonNode.size(); i++) {
                    JsonNode node = jsonNode.get(i);
                    String id = node.get("Processo").asText().trim();

                    if (!id.equals(p.getId())){
                        //logger.error("Erro ao coletar os considerandos do processo. ID diferente do solicitado: ID = " + id + ", solicitado = " + p.getId());
                        continue;
                    }

                    String tipo = node.get("TipoConsiderando").asText().trim();
                    String pessoa = "";
                    try { pessoa = node.get("NomePessoaConsiderando").asText().trim(); } catch (Exception e){}
                    String conteudo = node.get("Conteudo").asText().trim();

                    Document doc = Jsoup.parse(conteudo);
                    Elements htmlId = doc.getElementsByTag("IdProcessoConsiderandoITD");
                    String codigo = htmlId.text(); //Considerando's ID. Do nothing

                    Elements htmlContent = doc.getElementsByTag("p");
                    String limpo = htmlContent.text();  //Considerando's content
                    limpo = HtmlUtils.cleanAccentuation(limpo);

                    limpo = limpo.replaceAll("Considerando", "##### Considerando");
                    limpo = limpo.replaceAll("considerando", "##### Considerando");
                    limpo = limpo.replaceAll("CONSIDERANDO", "##### Considerando");

                    if (!limpo.isEmpty() && limpo.charAt(0) == '#'){
                        limpo = limpo.substring(6);
                    }

                    if (!tipo.isEmpty())
                        tiposSet.add(tipo); //não repete
                    if (!pessoa.isEmpty())
                        pessoasSet.add(pessoa);
                    conteudosList.add(conteudo); // repete
                    limposList.add(limpo);
                }

                //When finished, group everything together
                String tipos = tiposSet.stream().map(String::valueOf).collect(Collectors.joining(", "));
                String pessoas = pessoasSet.stream().map(String::valueOf).collect(Collectors.joining(", "));
                String conteudos = conteudosList.stream().map(String::valueOf).collect(Collectors.joining(" "));
                String limpos = limposList.stream().map(String::valueOf).collect(Collectors.joining("#####"));

                Considerando c = new Considerando();
                c.setIdProcesso(p.getId());
                c.setTipoConsiderando(tipos);
                c.setPessoaConsiderando(pessoas);
                c.setTextoOriginal(conteudos);
                c.setTextoLimpo(limpos);

                try {
                    considerandoRepository.save(c);
                } catch (Exception e){
                    logger.error("Erro salvando " + c, e);
                    throw new Exception(e);
                }
                salvosCount++;
                if (salvosCount % 50 == 0){
                    logger.info("Salvando considerandos. Salvos = " + salvosCount + " - " + c.getIdProcesso() + " - " + LocalDateTime.now());
                }

            } catch (URISyntaxException e) {
                //e.printStackTrace();
                logger.error("Erro ao coletar os considerandos do processo " + p.getId(), e);
            } catch (JsonMappingException e) {
                logger.error("Erro ao processar o JSON  do processo " + p.getId(), e);
            } catch (JsonProcessingException e) {
                logger.error("Erro ao processar o JSON  do processo " + p.getId(), e);
            } catch (Exception e) {
                logger.error("Erro ao processar o processo " + p.getId(), e);
            } finally {
            }
        }
        logger.info("Processos: " + processosCount + ". Salvos: " + salvosCount);
    }

    /**
     * Coleta as determinações dos processos transitados em julgado
     * Documentação do Endpoint: https://sistemas.tce.pe.gov.br/DadosAbertos/Exemplo!detalhar?dadosAbertos.id=39
     * Endpoint: https://sistemas.tce.pe.gov.br/DadosAbertos/Determinacoes!json
     * Em 27/03/2022 retornava 1974 resultados
     *
     * Ao invés de buscar tudo, faço diversas chamadas, uma para cada ID que já temos na base
     * Já lê as várias determinações e converte em uma só
     * Também checa se tem mais de uma UF e junta tudo
     *
     * ID de teste: 15100001-3
     * Consulta de 1 específico: https://sistemas.tce.pe.gov.br/DadosAbertos/Determinacoes!json?Processo=15100001-3
     */

    public void collectDeterminacoes() throws ExecutionException, InterruptedException, FileNotFoundException {

        List<Processo> processos = processoRepository.findAll();

        List<String> delimList = new DelimitersDetRec(delimPath).getDelimitadoresDetRec();

        String endpoint = "https://sistemas.tce.pe.gov.br/DadosAbertos/Determinacoes!json";

        HttpClient httpClient = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);

        int processosCount = 0;
        int salvosCount = 0;

        for (Processo p: processos){
            processosCount = processos.size();
            try {

                /*  //For debug
                if(p.getId().equals("15100300-2")){
                    System.out.println("Pare");
                } else { continue; } */

                URI uri = new URIBuilder(endpoint)
                        .addParameter("Processo", p.getId())
                        .build();
                HttpRequest httpRequest = HttpRequest.newBuilder(uri).GET().build();
                CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());

                //Processa a resposta
                JsonNode jsonNode = mapper.readTree(response.get().body()).findPath("conteudo");
                Set<String> nomeUjSet = new HashSet<String>();
                ArrayList<String> conteudosList = new ArrayList<String>(jsonNode.size());
                ArrayList<String> limposList = new ArrayList<String>(jsonNode.size());

                if (jsonNode.size() > 1){
                    logger.info("Tem gente com mais de 1: (" + jsonNode.size() + "): " + p.getId());
                }

                for(int i = 0; i < jsonNode.size(); i++) {
                    JsonNode node = jsonNode.get(i);
                    String id = node.get("Processo").asText().trim();

                    if (!id.equals(p.getId())){
                        //logger.error("Erro ao coletar os considerandos do processo. ID diferente do solicitado: ID = " + id + ", solicitado = " + p.getId());
                        continue;
                    }

                    String nomeUjDeterminacao = node.get("NomeUJDeterminacao").asText().trim();

                    //Esse conteúdo é sem padrão. Tem uns que vêm texto bom, outros vem com tag <IdProcessoDeterminacaoITD> dentro.
                    String conteudo = node.get("Conteudo").asText().trim();
                    Document doc = Jsoup.parse(conteudo);

                    //Retira as tags aleatórias
                    Elements remover = doc.getElementsByTag("IdProcessoDeterminacaoITD");
                    if (remover != null && !remover.isEmpty()) remover.remove();

                    //texto limpo
                    String limpo = doc.text();
                    limpo = HtmlUtils.cleanAccentuation(limpo);

                    for (String delim : delimList) {
                        limpo = limpo.replace("." + delim, ";#####" + delim);
                        limpo = limpo.replace(". " + delim, ";#####" + delim);
                        limpo = limpo.replace(";" + delim, ";#####" + delim);
                        limpo = limpo.replace("; " + delim, ";#####" + delim);
                    }

                    nomeUjSet.add(nomeUjDeterminacao);
                    conteudosList.add(conteudo);
                    limposList.add(limpo);
                }

                //When finished, group everything together
                String nomesUj = nomeUjSet.stream().map(String::valueOf).collect(Collectors.joining(", "));
                String conteudos = conteudosList.stream().map(String::valueOf).collect(Collectors.joining(" "));
                String limpos = limposList.stream().map(String::valueOf).collect(Collectors.joining("#####"));

                Determinacao d = new Determinacao();
                d.setIdProcesso(p.getId());
                d.setNomeUjDeterminacao(nomesUj);
                d.setTextoOriginal(conteudos);
                d.setTextoLimpo(limpos);

                try {
                    determinacaoRepository.save(d);
                } catch (Exception e){
                    logger.error("Erro salvando " + d, e);
                    throw new Exception(e);
                }
                salvosCount++;
                if (salvosCount % 50 == 0){
                    logger.info("Salvando determinacoes. Salvos = " + salvosCount + " - " + d.getIdProcesso() + " - " + LocalDateTime.now());
                }

            } catch (URISyntaxException e) {
                //e.printStackTrace();
                logger.error("Erro ao coletar os determinacoes do processo " + p.getId(), e);
            } catch (JsonMappingException e) {
                logger.error("Erro ao processar o JSON  do processo " + p.getId(), e);
            } catch (JsonProcessingException e) {
                logger.error("Erro ao processar o JSON  do processo " + p.getId(), e);
            } catch (Exception e) {
                logger.error("Erro ao processar o processo " + p.getId(), e);
            } finally {
            }
        }
        logger.info("Processos: " + processosCount + ". Salvos: " + salvosCount);
    }

    public void collectDeterminacoes(List<Processo> novosProcessos) throws ExecutionException, InterruptedException, FileNotFoundException {

        List<Processo> processos = novosProcessos;

        List<String> delimList = new DelimitersDetRec(delimPath).getDelimitadoresDetRec();

        String endpoint = "https://sistemas.tce.pe.gov.br/DadosAbertos/Determinacoes!json";

        HttpClient httpClient = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);

        int processosCount = 0;
        int salvosCount = 0;

        for (Processo p: processos){
            processosCount = processos.size();
            try {

                /*  //For debug
                if(p.getId().equals("15100300-2")){
                    System.out.println("Pare");
                } else { continue; } */

                URI uri = new URIBuilder(endpoint)
                        .addParameter("Processo", p.getId())
                        .build();
                HttpRequest httpRequest = HttpRequest.newBuilder(uri).GET().build();
                CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());

                //Processa a resposta
                JsonNode jsonNode = mapper.readTree(response.get().body()).findPath("conteudo");
                Set<String> nomeUjSet = new HashSet<String>();
                ArrayList<String> conteudosList = new ArrayList<String>(jsonNode.size());
                ArrayList<String> limposList = new ArrayList<String>(jsonNode.size());

                if (jsonNode.size() > 1){
                    logger.info("Tem gente com mais de 1: (" + jsonNode.size() + "): " + p.getId());
                }

                for(int i = 0; i < jsonNode.size(); i++) {
                    JsonNode node = jsonNode.get(i);
                    String id = node.get("Processo").asText().trim();

                    if (!id.equals(p.getId())){
                        //logger.error("Erro ao coletar os considerandos do processo. ID diferente do solicitado: ID = " + id + ", solicitado = " + p.getId());
                        continue;
                    }

                    String nomeUjDeterminacao = node.get("NomeUJDeterminacao").asText().trim();

                    //Esse conteúdo é sem padrão. Tem uns que vêm texto bom, outros vem com tag <IdProcessoDeterminacaoITD> dentro.
                    String conteudo = node.get("Conteudo").asText().trim();
                    Document doc = Jsoup.parse(conteudo);

                    //Retira as tags aleatórias
                    Elements remover = doc.getElementsByTag("IdProcessoDeterminacaoITD");
                    if (remover != null && !remover.isEmpty()) remover.remove();

                    //texto limpo
                    String limpo = doc.text();
                    limpo = HtmlUtils.cleanAccentuation(limpo);

                    for (String delim : delimList) {
                        limpo = limpo.replace("." + delim, ";#####" + delim);
                        limpo = limpo.replace(". " + delim, ";#####" + delim);
                        limpo = limpo.replace(";" + delim, ";#####" + delim);
                        limpo = limpo.replace("; " + delim, ";#####" + delim);
                    }

                    nomeUjSet.add(nomeUjDeterminacao);
                    conteudosList.add(conteudo);
                    limposList.add(limpo);
                }

                //When finished, group everything together
                String nomesUj = nomeUjSet.stream().map(String::valueOf).collect(Collectors.joining(", "));
                String conteudos = conteudosList.stream().map(String::valueOf).collect(Collectors.joining(" "));
                String limpos = limposList.stream().map(String::valueOf).collect(Collectors.joining("#####"));

                Determinacao d = new Determinacao();
                d.setIdProcesso(p.getId());
                d.setNomeUjDeterminacao(nomesUj);
                d.setTextoOriginal(conteudos);
                d.setTextoLimpo(limpos);

                try {
                    determinacaoRepository.save(d);
                } catch (Exception e){
                    logger.error("Erro salvando " + d, e);
                    throw new Exception(e);
                }
                salvosCount++;
                if (salvosCount % 50 == 0){
                    logger.info("Salvando determinacoes. Salvos = " + salvosCount + " - " + d.getIdProcesso() + " - " + LocalDateTime.now());
                }

            } catch (URISyntaxException e) {
                //e.printStackTrace();
                logger.error("Erro ao coletar os determinacoes do processo " + p.getId(), e);
            } catch (JsonMappingException e) {
                logger.error("Erro ao processar o JSON  do processo " + p.getId(), e);
            } catch (JsonProcessingException e) {
                logger.error("Erro ao processar o JSON  do processo " + p.getId(), e);
            } catch (Exception e) {
                logger.error("Erro ao processar o processo " + p.getId(), e);
            } finally {
            }
        }
        logger.info("Processos: " + processosCount + ". Salvos: " + salvosCount);
    }

    /**
     * Coleta as recomendações dos processos transitados em julgado
     * Documentação do Endpoint: https://sistemas.tce.pe.gov.br/DadosAbertos/Exemplo!detalhar?dadosAbertos.id=40
     * Endpoint:https://sistemas.tce.pe.gov.br/DadosAbertos/Recomendacoes!json
     * Em 27/03/2022 retornava 464 resultados
     *
     * Ao invés de buscar tudo, faço diversas chamadas, uma para cada ID que já temos na base
     * Já lê as várias recomendações e converte em uma só
     *
     * ID de teste: 15100001-3
     * Consulta de 1 específico: https://sistemas.tce.pe.gov.br/DadosAbertos/Recomendacoes!json?Processo=15100004-9
     */

    public void collectRecomendacoes() throws ExecutionException, InterruptedException, FileNotFoundException {

        List<Processo> processos = processoRepository.findAll();

        List<String> delimList = new DelimitersDetRec(delimPath).getDelimitadoresDetRec();

        String endpoint = "https://sistemas.tce.pe.gov.br/DadosAbertos/Recomendacoes!json";

        HttpClient httpClient = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);

        int processosCount = 0;
        int salvosCount = 0;

        for (Processo p: processos){
            processosCount = processos.size();
            try {

                /*  //For debug
                if(p.getId().equals("15100300-2")){
                    System.out.println("Pare");
                } else { continue; } */

                URI uri = new URIBuilder(endpoint)
                        .addParameter("Processo", p.getId())
                        .build();
                HttpRequest httpRequest = HttpRequest.newBuilder(uri).GET().build();
                CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());

                //Processa a resposta
                JsonNode jsonNode = mapper.readTree(response.get().body()).findPath("conteudo");
                Set<String> nomeUjSet = new HashSet<String>();
                ArrayList<String> conteudosList = new ArrayList<String>(jsonNode.size());
                ArrayList<String> limposList = new ArrayList<String>(jsonNode.size());

                if (jsonNode.size() > 1){
                    logger.info("Tem gente com mais de 1: (" + jsonNode.size() + "): " + p.getId());
                }

                for(int i = 0; i < jsonNode.size(); i++) {
                    JsonNode node = jsonNode.get(i);
                    String id = node.get("Processo").asText().trim();

                    if (!id.equals(p.getId())){
                        //logger.error("Erro ao coletar os considerandos do processo. ID diferente do solicitado: ID = " + id + ", solicitado = " + p.getId());
                        continue;
                    }

                    String nomeUjDeterminacao = node.get("NomeUJRecomendacao").asText().trim();

                    //Esse conteúdo é sem padrão. Tem uns que vêm texto bom, outros vem com tag <IdProcessoDeterminacaoITD> dentro.
                    String conteudo = node.get("Conteudo").asText().trim();
                    Document doc = Jsoup.parse(conteudo);

                    //Retira as tags aleatórias
                    Elements remover = doc.getElementsByTag("IdProcessoRecomendacaoITD");
                    if (remover != null && !remover.isEmpty()) remover.remove();

                    //texto limpo
                    String limpo = doc.text();
                    limpo = HtmlUtils.cleanAccentuation(limpo);

                    for (String delim : delimList) {
                        limpo = limpo.replace("." + delim, ";#####" + delim);
                        limpo = limpo.replace(". " + delim, ";#####" + delim);
                        limpo = limpo.replace(";" + delim, ";#####" + delim);
                        limpo = limpo.replace("; " + delim, ";#####" + delim);
                    }

                    nomeUjSet.add(nomeUjDeterminacao);
                    conteudosList.add(conteudo);
                    limposList.add(limpo);
                }

                //When finished, group everything together
                String nomesUj = nomeUjSet.stream().map(String::valueOf).collect(Collectors.joining(", "));
                String conteudos = conteudosList.stream().map(String::valueOf).collect(Collectors.joining(" "));
                String limpos = limposList.stream().map(String::valueOf).collect(Collectors.joining("#####"));

                Recomendacao r = new Recomendacao();
                r.setIdProcesso(p.getId());
                r.setNomeUjRecomendacao(nomesUj);
                r.setTextoOriginal(conteudos);
                r.setTextoLimpo(limpos);

                try {
                    recomendacaoRepository.save(r);
                } catch (Exception e){
                    logger.error("Erro salvando recomendacao: " + r, e);
                    throw new Exception(e);
                }
                salvosCount++;
                if (salvosCount % 50 == 0){
                    logger.info("Salvando recomendacoes. Salvos = " + salvosCount + " - " + r.getIdProcesso() + " - " + LocalDateTime.now());
                }

            } catch (URISyntaxException e) {
                //e.printStackTrace();
                logger.error("Erro ao coletar os recomendacoes do processo " + p.getId(), e);
            } catch (JsonMappingException e) {
                logger.error("Erro ao processar o JSON  do processo " + p.getId(), e);
            } catch (JsonProcessingException e) {
                logger.error("Erro ao processar o JSON  do processo " + p.getId(), e);
            } catch (Exception e) {
                logger.error("Erro ao processar o processo " + p.getId(), e);
            } finally {
            }
        }
        logger.info("Processos: " + processosCount + ". Salvos: " + salvosCount);
    }

    public void collectRecomendacoes(List<Processo> novosProcessos) throws ExecutionException, InterruptedException, FileNotFoundException {

        List<Processo> processos = novosProcessos;

        List<String> delimList = new DelimitersDetRec(delimPath).getDelimitadoresDetRec();

        String endpoint = "https://sistemas.tce.pe.gov.br/DadosAbertos/Recomendacoes!json";

        HttpClient httpClient = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);

        int processosCount = 0;
        int salvosCount = 0;

        for (Processo p: processos){
            processosCount = processos.size();
            try {

                /*  //For debug
                if(p.getId().equals("15100300-2")){
                    System.out.println("Pare");
                } else { continue; } */

                URI uri = new URIBuilder(endpoint)
                        .addParameter("Processo", p.getId())
                        .build();
                HttpRequest httpRequest = HttpRequest.newBuilder(uri).GET().build();
                CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());

                //Processa a resposta
                JsonNode jsonNode = mapper.readTree(response.get().body()).findPath("conteudo");
                Set<String> nomeUjSet = new HashSet<String>();
                ArrayList<String> conteudosList = new ArrayList<String>(jsonNode.size());
                ArrayList<String> limposList = new ArrayList<String>(jsonNode.size());

                if (jsonNode.size() > 1){
                    logger.info("Tem gente com mais de 1: (" + jsonNode.size() + "): " + p.getId());
                }

                for(int i = 0; i < jsonNode.size(); i++) {
                    JsonNode node = jsonNode.get(i);
                    String id = node.get("Processo").asText().trim();

                    if (!id.equals(p.getId())){
                        //logger.error("Erro ao coletar os considerandos do processo. ID diferente do solicitado: ID = " + id + ", solicitado = " + p.getId());
                        continue;
                    }

                    String nomeUjDeterminacao = node.get("NomeUJRecomendacao").asText().trim();

                    //Esse conteúdo é sem padrão. Tem uns que vêm texto bom, outros vem com tag <IdProcessoDeterminacaoITD> dentro.
                    String conteudo = node.get("Conteudo").asText().trim();
                    Document doc = Jsoup.parse(conteudo);

                    //Retira as tags aleatórias
                    Elements remover = doc.getElementsByTag("IdProcessoRecomendacaoITD");
                    if (remover != null && !remover.isEmpty()) remover.remove();

                    //texto limpo
                    String limpo = doc.text();
                    limpo = HtmlUtils.cleanAccentuation(limpo);

                    for (String delim : delimList) {
                        limpo = limpo.replace("." + delim, ";#####" + delim);
                        limpo = limpo.replace(". " + delim, ";#####" + delim);
                        limpo = limpo.replace(";" + delim, ";#####" + delim);
                        limpo = limpo.replace("; " + delim, ";#####" + delim);
                    }

                    nomeUjSet.add(nomeUjDeterminacao);
                    conteudosList.add(conteudo);
                    limposList.add(limpo);
                }

                //When finished, group everything together
                String nomesUj = nomeUjSet.stream().map(String::valueOf).collect(Collectors.joining(", "));
                String conteudos = conteudosList.stream().map(String::valueOf).collect(Collectors.joining(" "));
                String limpos = limposList.stream().map(String::valueOf).collect(Collectors.joining("#####"));

                Recomendacao r = new Recomendacao();
                r.setIdProcesso(p.getId());
                r.setNomeUjRecomendacao(nomesUj);
                r.setTextoOriginal(conteudos);
                r.setTextoLimpo(limpos);

                try {
                    recomendacaoRepository.save(r);
                } catch (Exception e){
                    logger.error("Erro salvando recomendacao: " + r, e);
                    throw new Exception(e);
                }
                salvosCount++;
                if (salvosCount % 50 == 0){
                    logger.info("Salvando recomendacoes. Salvos = " + salvosCount + " - " + r.getIdProcesso() + " - " + LocalDateTime.now());
                }

            } catch (URISyntaxException e) {
                //e.printStackTrace();
                logger.error("Erro ao coletar os recomendacoes do processo " + p.getId(), e);
            } catch (JsonMappingException e) {
                logger.error("Erro ao processar o JSON  do processo " + p.getId(), e);
            } catch (JsonProcessingException e) {
                logger.error("Erro ao processar o JSON  do processo " + p.getId(), e);
            } catch (Exception e) {
                logger.error("Erro ao processar o processo " + p.getId(), e);
            } finally {
            }
        }
        logger.info("Processos: " + processosCount + ". Salvos: " + salvosCount);
    }

    public void collectLimiteGastos() throws ExecutionException, InterruptedException{
        LimiteDeGastosMapper mapperLimiteDeGastos = new LimiteDeGastosMapper();
        String endpoint = "https://sistemas.tce.pe.gov.br/DadosAbertos/DadosLimiteGastos!json?";
        List<Processo> processos = processoRepository.findAll();
        HttpClient httpClient = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        URI uri = null;
        int contador = 0;
        try {
            for(Processo processo : processos){
                contador++;

                uri = new URIBuilder(endpoint)
                        .addParameter("nr_processo", processo.getId())
                        .build();

                HttpRequest httpRequest = HttpRequest.newBuilder(uri).GET().build();
                CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());

                //Processa a resposta
                JsonNode qteConteudoJson = mapper.readTree(response.get().body()).findPath("tamanhoResultado");
                int qteConteudo = Integer.parseInt(qteConteudoJson.asText());
                JsonNode jsonNode = mapper.readTree(response.get().body()).findPath("conteudo"); // lista com os limites de gasto
                // todo: Coletar os dados da lista Json que a linha de código acima me fornece e colocar ela em registros da tabela limite de gastos.
                for(int i = 0; i < qteConteudo; i++){
                    LimiteDeGastos limiteDeGastos = mapperLimiteDeGastos.toEntity(jsonNode.get(i));
                    limiteDeGastosRepository.save(limiteDeGastos);
                }
                if(contador%100 == 0){
                    System.out.println("Processo : " + contador );
                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void collectLimiteGastos(List<Processo> novosProcessos) {
        LimiteDeGastosMapper mapperLimiteDeGastos = new LimiteDeGastosMapper();
        String endpoint = "https://sistemas.tce.pe.gov.br/DadosAbertos/DadosLimiteGastos!json?";
        List<Processo> processos = novosProcessos;
        HttpClient httpClient = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        URI uri = null;
        int contador = 0;
        try {
            for(Processo processo : processos){
                contador++;

                uri = new URIBuilder(endpoint)
                        .addParameter("nr_processo", processo.getId())
                        .build();

                HttpRequest httpRequest = HttpRequest.newBuilder(uri).GET().build();
                CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());

                //Processa a resposta
                JsonNode qteConteudoJson = mapper.readTree(response.get().body()).findPath("tamanhoResultado");
                int qteConteudo = Integer.parseInt(qteConteudoJson.asText());
                JsonNode jsonNode = mapper.readTree(response.get().body()).findPath("conteudo"); // lista com os limites de gasto
                // todo: Coletar os dados da lista Json que a linha de código acima me fornece e colocar ela em registros da tabela limite de gastos.
                for(int i = 0; i < qteConteudo; i++){
                    LimiteDeGastos limiteDeGastos = mapperLimiteDeGastos.toEntity(jsonNode.get(i));
                    limiteDeGastosRepository.save(limiteDeGastos);
                }
                if(contador%100 == 0){
                    System.out.println("Processo : " + contador );
                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public String simplificacao() {

        return "";
    }
}

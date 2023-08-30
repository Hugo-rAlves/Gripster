package org.ufrpe.inovagovlab.decisoestce.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.ufrpe.inovagovlab.decisoestce.mapper.MapperApi;
import org.ufrpe.inovagovlab.decisoestce.model.Processo;
import org.ufrpe.inovagovlab.decisoestce.model.dto.*;
import org.ufrpe.inovagovlab.decisoestce.repository.ConsiderandoRepository;
import org.ufrpe.inovagovlab.decisoestce.repository.DeterminacaoRepository;
import org.ufrpe.inovagovlab.decisoestce.repository.ProcessoRepository;
import org.ufrpe.inovagovlab.decisoestce.repository.RecomendacaoRepository;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ApiService {

    @Autowired
    private ProcessoRepository processoRepository;
    @Autowired
    private MapperApi map;
    @Autowired
    private ConsiderandoRepository considerandoRepository;
    @Autowired
    private RecomendacaoRepository recomendacaoRepository;
    @Autowired
    private DeterminacaoRepository determinacaoRepository;




    public List<String> getAllIdsProcessos() throws Exception {
        List<String> idsProcessos = processoRepository.getAllIdsProcessos().orElseThrow(() -> new Exception("Dado não encontrado"));
        return idsProcessos;
    }

    public List<GestoresDTO> getProcessosGestor() {
        List<String> gestores = processoRepository.findGestoresPrestacaoDeContas().orElseThrow();
        Collections.sort(gestores);
        return map.mapperNomesToGestoresDTO(gestores);
    }

    public List<CardProcessoList> getProcessosGestor(String id) {
        List<Processo> processosDeUmGestor = processoRepository.findProcessosGestor(id).orElseThrow();
        return map.mapProcessoToCardProcessoList(processosDeUmGestor);
    }

    public List<MunicipiosDto> getMunicipios() {
        List<String> municipiosDtos = processoRepository.findMunicipiosPrestacaoDeContas().orElseThrow(()-> new RuntimeException("Erro"));
        Collections.sort(municipiosDtos);
        return  map.mapperMunicipioToDto(municipiosDtos);
    }

    public List<MeuTextoDTO> getMeusTextos() {
        return null;
    }

    public List<MeuTextoDTO> getMeusTextos(String id) {
        return null;
    }

    public MeuTextoSimplificadoDTO salvarMeuTexto(MeuTextoDTO meuTextoDTO) {
        return null;
    }

    public void favoritarTexto(String id) {
    }

    public List<CardProcessoList> getProcessosMunicipio(String id) {
        List<Processo> processosDeUmMunicipio = processoRepository.findProcessosMunicipio(id).orElseThrow();
        return map.mapProcessoToCardProcessoList(processosDeUmMunicipio);

    }

    public CardInformacoesGeraisDto getQuadroInformacaoGeral(String id) {
        Processo processo = processoRepository.findById(id).orElseThrow();
        CardInformacoesGeraisDto cardDto = map.mapperProcessoToInfGeralDto(processo);

        return cardDto;

    }

    public  CardDecisaoSimplificada getSimplificacaoDecisao(String id) throws IOException {
        String simplConsiracoes = considerandoRepository.getTextoCompleto(id).orElseThrow();
        String simplRecomendacoes = recomendacaoRepository.getTextoCompleto(id).orElseThrow();
        String simplDeterminacoes = determinacaoRepository.getTextoCompleto(id).orElseThrow();

        HttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost("https://api.openai.com/v1/chat/completions");

        // Configurar cabeçalhos e corpo da solicitação
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Authorization", "Bearer \\API KEY");

        String requestBodyConsideracoes = "{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"system\", \"content\": \"You are a legal assistant that helps simplify legal texts for common language in brazilian portuguese.\"}, {\"role\": \"user\", \"content\": \"" + simplConsiracoes + "\"}]}";
        String requestBodyRecomendacoes = "{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"system\", \"content\": \"You are a legal assistant that helps simplify legal texts for common language in brazilian portuguese.\"}, {\"role\": \"user\", \"content\": \"" + simplRecomendacoes + "\"}]}";
        String requestBodyDeterminacoes = "{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"system\", \"content\": \"You are a legal assistant that helps simplify legal texts for common language in brazilian portuguese.\"}, {\"role\": \"user\", \"content\": \"" + simplDeterminacoes + "\"}]}";

        request.setEntity(new StringEntity(requestBodyConsideracoes));
        HttpResponse responseConsideracoes = httpClient.execute(request);

        request.setEntity(new StringEntity(requestBodyRecomendacoes));
        HttpResponse responseRecomendacoes = httpClient.execute(request);

        request.setEntity(new StringEntity(requestBodyDeterminacoes));
        HttpResponse responseDeterminacoes = httpClient.execute(request);

        CardDecisaoSimplificada card = new CardDecisaoSimplificada();

        card.setTextoSimplificadoConsiderando(extractSimplifiedTextFromJSON(simplConsiracoes));
        card.setTextoSimplificadoRecomendacao(extractSimplifiedTextFromJSON(simplRecomendacoes));
        card.setTextoSimplificadoDeterminando(extractSimplifiedTextFromJSON(simplDeterminacoes));

        return card;
    }

    public TextoCompletoDTO getTextoCompletoProcesso(String id) {
        String texto = "";
        TextoCompletoDTO textoCompletoDTO = new TextoCompletoDTO();

        texto = considerandoRepository.getTextoCompleto(id).orElseThrow();
        if(texto != null){
            textoCompletoDTO.setConsiderandos(Arrays.asList(texto.split("#####")));
        }
        texto = recomendacaoRepository.getTextoCompleto(id).orElseThrow();
        if(texto != null) {
            textoCompletoDTO.setRecomendacoes(Arrays.asList(texto.split("#####")));
        }
        texto = determinacaoRepository.getTextoCompleto(id).orElseThrow();
        if(texto != null){
            textoCompletoDTO.setDeterminacoes(Arrays.asList(texto.split("#####")));
        }
        return textoCompletoDTO;
    }

    public Integer getNumeroDeProcessos() {
        Integer numeroProcessos = processoRepository.countNumeroProcessosPrestacaoDeContas();
        return numeroProcessos;
    }

    public Integer getNumeroDeMunicipios() {
        Integer numeroMunicipios = processoRepository.countNumeroMunicipios();
        return numeroMunicipios;
    }

    public Integer getNumeroGestores() {
        Integer numeroGestores = processoRepository.countNumeroGestores();
        return numeroGestores;
    }

    public String extractSimplifiedTextFromJSON(String jsonResponse) {
        JSONParser parser = new JSONParser();

        try {
            JSONObject responseObj = (JSONObject) parser.parse(jsonResponse);
            JSONArray choicesArray = (JSONArray) responseObj.get("choices");

            if (choicesArray != null && choicesArray.size() > 0) {
                JSONObject messageObj = (JSONObject) ((JSONObject) choicesArray.get(0)).get("message");
                if (messageObj != null) {
                    return (String) messageObj.get("content");
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }
}

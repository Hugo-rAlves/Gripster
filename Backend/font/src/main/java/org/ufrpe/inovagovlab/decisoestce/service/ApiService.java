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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
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

    @Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    @Value("${openai.model}")
    private String model;
    @Value("${openai.api.url}")
    private String apiUrl;




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

        CardDecisaoSimplificada card = new CardDecisaoSimplificada();

        Processo processo = processoRepository.getSimplificacaoTextualDoProcesso(id).orElseThrow();

        if( processo.getSuperSummary() == null){
            //        String diretiva = "O texto abaixo tem um vocabulário muito jurídico e ele também é muito extenso. Resuma o texto, pegando o que tem de mais importante e com uma linguagem bem simples: ";
            String diretiva = "O texto enviado a seguir trata de uma decisão judicial das contas do governo, que tem como possível resultado Aprovado, Reprovado e Aprovado com Ressalvas. Simplifique o texto enviado a Seguir: ";
            TextoCompletoDTO textCompleto = getTextoCompletoProcesso(id);
            String texto = "";

            if(textCompleto.getConsiderandos()!=null){
                texto = texto + String.join(" ",textCompleto.getConsiderandos());
            }
            if (textCompleto.getRecomendacoes()!=null){
                texto = texto + String.join(" ",textCompleto.getRecomendacoes());
            }
            if (textCompleto.getDeterminacoes()!=null){
                texto = texto + String.join(" ",textCompleto.getDeterminacoes());
            }

            String prompt = diretiva + String.join(" ",texto);
            ChatResponse cr = getRespostaGpt(prompt);
            String simplificacao = cr.getChoices().get(0).getMessage().getContent();
            processo.setSuperSummary(simplificacao);
            processoRepository.save(processo);
            card.setTextoSimplificado(simplificacao);

        }else {
            card.setTextoSimplificado(processo.getSuperSummary());
        }
        return card;

    }

    private ChatResponse getRespostaGpt(String prompt){
        ChatRequest request = new ChatRequest(model, prompt);
        ChatResponse response = restTemplate.postForObject(apiUrl, request, ChatResponse.class);
        return response;
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

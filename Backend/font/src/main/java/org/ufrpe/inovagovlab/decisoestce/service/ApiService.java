package org.ufrpe.inovagovlab.decisoestce.service;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.ufrpe.inovagovlab.decisoestce.mapper.MapperApi;
import org.ufrpe.inovagovlab.decisoestce.model.Processo;
import org.ufrpe.inovagovlab.decisoestce.model.dto.*;
import org.ufrpe.inovagovlab.decisoestce.repository.ConsiderandoRepository;
import org.ufrpe.inovagovlab.decisoestce.repository.DeterminacaoRepository;
import org.ufrpe.inovagovlab.decisoestce.repository.ProcessoRepository;
import org.ufrpe.inovagovlab.decisoestce.repository.RecomendacaoRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    @Value("${peMap.path}")
    private String peMap;


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

    public  CardDecisaoSimplificada getSimplificacaoDecisao(String id) {
        String simplConsiracoes = considerandoRepository.getSimplificacao(id).orElseThrow();
        String simplRecomendacoes = recomendacaoRepository.getSimplificacao(id).orElseThrow();
        String simplDeterminacoes = determinacaoRepository.getSimplificacao(id).orElseThrow();

        CardDecisaoSimplificada card = new CardDecisaoSimplificada();

        card.setTextoSimplificadoRecomendacao(simplRecomendacoes);
        card.setTextoSimplificadoDeterminando(simplDeterminacoes);
        card.setTextoSimplificadoConsiderando(simplConsiracoes);

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

    public List<CodigoValorMuncipio> getDadosMapa() {
        List<Object[]> query = processoRepository.getDadosMapa();
        List<CodigoValorMuncipio> listaMunicipios = map.mapToMunicipiosValores(query);
        return listaMunicipios;
    }
    public Object getJson() throws IOException {
        String str = new String(Files.readAllBytes(Paths.get(peMap)));
        return str;
    }
}

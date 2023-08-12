package org.ufrpe.inovagovlab.decisoestce.service;

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
        return map.mapperNomesToGestoresDTO(gestores);
    }

    public List<CardProcessoList> getProcessosGestor(String id) {
        List<Processo> processosDeUmGestor = processoRepository.findProcessosGestor(id).orElseThrow();
        return map.mapProcessoToCardProcessoList(processosDeUmGestor);
    }

    public List<MunicipiosDto> getMunicipios() {
        List<String> municipiosDtos = processoRepository.findMunicipiosPrestacaoDeContas().orElseThrow(()-> new RuntimeException("Erro"));
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
        return null;
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
}

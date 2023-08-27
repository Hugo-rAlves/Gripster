package org.ufrpe.inovagovlab.decisoestce.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ufrpe.inovagovlab.decisoestce.model.dto.*;
import org.ufrpe.inovagovlab.decisoestce.service.ApiService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/app")
public class ApiController {

    @Autowired
    private ApiService apiService;

    @GetMapping(value = "/get-numeros-processos-pretacao-de-contas")
    public ResponseEntity<?> getNrsProcessos() throws Exception {
        List<String> buscarTodosOsProcessos = apiService.getAllIdsProcessos();
        return ResponseEntity.ok(buscarTodosOsProcessos);
    }
    @GetMapping(value = "/get-processos")
    public ResponseEntity<?> getNumeroProcessos(){
        Integer numeroDeProcessos = apiService.getNumeroDeProcessos();
        return ResponseEntity.ok(numeroDeProcessos);
    }
    @GetMapping(value = "/get-numero-municipios")
    public ResponseEntity<?> getNumeroMunicipios(){
        Integer buscarTodosOsProcessos = apiService.getNumeroDeMunicipios();
        return ResponseEntity.ok(buscarTodosOsProcessos);
    }
    @GetMapping(value = "/get-numero-gestores")
    public ResponseEntity<?> getNumeroGestores(){
        Integer numeroGestores = apiService.getNumeroGestores();
        return ResponseEntity.ok(numeroGestores);
    }
    @GetMapping(value = "/get-gestores", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getGestores(){
        List<GestoresDTO> gestores = apiService.getProcessosGestor();
        return ResponseEntity.ok(gestores);
    }
    @GetMapping(value = "/get-municipios", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMunicipios(){
        List<MunicipiosDto> gestores = apiService.getMunicipios();
        return ResponseEntity.ok(gestores);
    }
    @GetMapping(value = "/get-texto-completo/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTextoCompletoProcessoConsiderando(@PathVariable String id){
        TextoCompletoDTO textoCompleto = apiService.getTextoCompletoProcesso(id);
        return ResponseEntity.ok(textoCompleto);
    }

    @GetMapping(value = "/get-informacoes-gerais-processo/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getInformationBoardProcessos(@PathVariable String id){
        CardInformacoesGeraisDto informationBoard = apiService.getQuadroInformacaoGeral(id);
        return ResponseEntity.ok(informationBoard);
    }
    @GetMapping(value = "/get-simplificacao-decisao/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSimplificacaoDecisao(@PathVariable String id){
        CardDecisaoSimplificada decisaoSimplificada = apiService.getSimplificacaoDecisao(id);
        return ResponseEntity.ok(decisaoSimplificada);
    }
    @GetMapping(value = "/get-processos-gestor/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getGestor(@PathVariable String id){
        List<CardProcessoList> gestores = apiService.getProcessosGestor(id);
        return ResponseEntity.ok(gestores);
    }
    @GetMapping(value = "/get-processos-municipio/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMunicipio(@PathVariable String id){
        List<CardProcessoList> gestores = apiService.getProcessosMunicipio(id);
        return ResponseEntity.ok(gestores);
    }
    @GetMapping(value = "/get-limite-de-gastos/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getLimiteDeGastosProcesso(@PathVariable String id){
        return null;
    }
    @PostMapping(value = "/favoritar-texto-tribunal/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> favoritarTextoDoTribunal(@PathVariable String id){
        apiService.favoritarTexto(id);
        return ResponseEntity.ok("Texto Favoritado");
    }


}

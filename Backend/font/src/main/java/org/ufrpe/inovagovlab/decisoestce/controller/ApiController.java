package org.ufrpe.inovagovlab.decisoestce.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ufrpe.inovagovlab.decisoestce.model.dto.MeuTextoSimplificadoDTO;
import org.ufrpe.inovagovlab.decisoestce.model.dto.GestoresDTO;
import org.ufrpe.inovagovlab.decisoestce.model.dto.MeuTextoDTO;
import org.ufrpe.inovagovlab.decisoestce.model.dto.ProcessoDTO;
import org.ufrpe.inovagovlab.decisoestce.service.ApiService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/app")
public class ApiController {

    @Autowired
    private ApiService apiService;

    @GetMapping(value = "/get-numeros-processos-pretacao-de-contas")
    public ResponseEntity<?> getNrsProcessos(){
        List<ProcessoDTO> buscarTodosOsProcessos = apiService.getAllProcessos();
        return ResponseEntity.ok(buscarTodosOsProcessos);
    }
    @GetMapping(value = "/get-consideracoes-original-processo-pretacao-de-contas")
    public ResponseEntity<?> getConsideracoesProcessos(){
        List<ProcessoDTO> buscarTodosOsProcessos = apiService.getAllProcessos();
        return ResponseEntity.ok(buscarTodosOsProcessos);
    }
    @GetMapping(value = "/get-consideracoes-simplificadas-processo-pretacao-de-contas")
    public ResponseEntity<?> getConsideracoesSimplificadasProcessos(){
        List<ProcessoDTO> buscarTodosOsProcessos = apiService.getAllProcessos();
        return ResponseEntity.ok(buscarTodosOsProcessos);
    }
    @GetMapping(value = "/get-determinacoes-processo-pretacao-de-contas")
    public ResponseEntity<?> getDeterminacoesProcessos(){
        List<ProcessoDTO> buscarTodosOsProcessos = apiService.getAllProcessos();
        return ResponseEntity.ok(buscarTodosOsProcessos);
    }
    @GetMapping(value = "/get-painel-de-informacao-processo-pretacao-de-contas")
    public ResponseEntity<?> getInformationBoardProcessos(){
        List<ProcessoDTO> buscarTodosOsProcessos = apiService.getAllProcessos();
        return ResponseEntity.ok(buscarTodosOsProcessos);
    }
    @GetMapping(value = "/get-gestores")
    public ResponseEntity<?> getGestores(){
        List<GestoresDTO> gestores = apiService.getGestores();
        return ResponseEntity.ok(gestores);
    }
    @GetMapping(value = "/get-gestores/{id}")
    public ResponseEntity<?> getGestor(@PathVariable String id){
        List<GestoresDTO> gestores = apiService.getGestores(id);
        return ResponseEntity.ok(gestores);
    }
    @GetMapping(value = "/get-municipios")
    public ResponseEntity<?> getMunicipios(){
        List<GestoresDTO> gestores = apiService.getMunicipios();
        return ResponseEntity.ok(gestores);
    }
    @GetMapping(value = "/get-municipios/{id}")
    public ResponseEntity<?> getMunicipio(@PathVariable String id){
        List<GestoresDTO> gestores = apiService.getMunicipios(id);
        return ResponseEntity.ok(gestores);
    }
    @GetMapping(value = "/get-meus-textos")
    public ResponseEntity<?> getMeusTextos(){
        List<MeuTextoDTO> meusTextos = apiService.getMeusTextos();
        return ResponseEntity.ok(meusTextos);
    }
    @GetMapping(value = "/get-meus-textos/{id}")
    public ResponseEntity<?> getMeusTexto(@PathVariable String id){
        List<MeuTextoDTO> meusTextos = apiService.getMeusTextos(id);
        return ResponseEntity.ok(meusTextos);
    }
    @PostMapping(value = "/salvar-meu-texto")
    public ResponseEntity<?> salvarMeuTexto(@RequestBody MeuTextoDTO meuTextoDTO){
        MeuTextoSimplificadoDTO textoSimplificado = apiService.salvarMeuTexto(meuTextoDTO);
        return ResponseEntity.ok(textoSimplificado);
    }
    @PostMapping(value = "/favoritar-texto-tribunal/{id}")
    public ResponseEntity<?> favoritarTextoDoTribunal(@PathVariable String id){
        apiService.favoritarTexto(id);
        return ResponseEntity.ok("Texto Favoritado");
    }



}

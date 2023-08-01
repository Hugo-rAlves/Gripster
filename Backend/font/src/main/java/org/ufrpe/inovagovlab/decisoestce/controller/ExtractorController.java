package org.ufrpe.inovagovlab.decisoestce.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ufrpe.inovagovlab.decisoestce.service.ExtractorServiceImpl;

import java.io.FileNotFoundException;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/extractor")
public class ExtractorController {

    private static final Logger logger = LoggerFactory.getLogger(ExtractorController.class);

    @Autowired
    private ExtractorServiceImpl extractorService;

    @GetMapping("/run/processos")
    public String runProcessos() {
        try {
            extractorService.collectProcessos();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "main";
    }

    @GetMapping("/run/considerandos")
    public String runConsiderandos() {
        try {
            extractorService.collectConsiderandos();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "main";
    }

    @GetMapping("/run/determinacoes")
    public String runDeterminacoes() {
        try {
            extractorService.collectDeterminacoes();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return "main";
    }

    @GetMapping("/run/recomendacoes")
    public String runRecomendacoes() {
        try {
            extractorService.collectRecomendacoes();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return "main";
    }
    @GetMapping("/run/limiteDeGastos")
    public String runLimiteDeGastos() {
        try {
            extractorService.collectLimiteGastos();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "main";
    }

    @GetMapping("/run/simplificacao")
    public String runSimplificacaoConsiderando(){
        String resultado = extractorService.simplificacao();
        return "main";
    }


}

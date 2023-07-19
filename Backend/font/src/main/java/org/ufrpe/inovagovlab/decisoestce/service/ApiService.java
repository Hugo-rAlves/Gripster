package org.ufrpe.inovagovlab.decisoestce.service;

import org.springframework.stereotype.Service;
import org.ufrpe.inovagovlab.decisoestce.model.dto.GestoresDTO;
import org.ufrpe.inovagovlab.decisoestce.model.dto.MeuTextoDTO;
import org.ufrpe.inovagovlab.decisoestce.model.dto.MeuTextoSimplificadoDTO;
import org.ufrpe.inovagovlab.decisoestce.model.dto.ProcessoDTO;

import java.util.List;

@Service
public class ApiService {
    public List<ProcessoDTO> getAllProcessos() {
        return null;
    }

    public List<GestoresDTO> getGestores() {
        return null;
    }

    public List<GestoresDTO> getGestores(String id) {
        return null;
    }

    public List<GestoresDTO> getMunicipios() {
        return  null;
    }

    public List<GestoresDTO> getMunicipios(String id) {
        return  null;

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
}

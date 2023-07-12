package org.ufrpe.inovagovlab.decisoestce.model.dto;

import java.util.List;

public class TextoCompletoDTO {
    private List<String> considerandos;
    private List<String> determinacoes;
    private List<String> recomendacoes;

    public List<String> getConsiderandos() {
        return considerandos;
    }

    public void setConsiderandos(List<String> considerandos) {
        this.considerandos = considerandos;
    }

    public List<String> getDeterminacoes() {
        return determinacoes;
    }

    public void setDeterminacoes(List<String> determinacoes) {
        this.determinacoes = determinacoes;
    }

    public List<String> getRecomendacoes() {
        return recomendacoes;
    }

    public void setRecomendacoes(List<String> recomendacoes) {
        this.recomendacoes = recomendacoes;
    }
}

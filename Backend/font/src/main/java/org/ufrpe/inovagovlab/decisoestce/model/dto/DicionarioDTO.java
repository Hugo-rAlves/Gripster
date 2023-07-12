package org.ufrpe.inovagovlab.decisoestce.model.dto;

public class DicionarioDTO {

    private String palavra;
    private String significado;

    public String getPalavra() {
        return palavra;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }

    public String getSignificado() {
        return significado;
    }

    public void setSignificado(String significado) {
        this.significado = significado;
    }
}

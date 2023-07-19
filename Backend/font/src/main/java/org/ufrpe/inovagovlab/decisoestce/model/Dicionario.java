package org.ufrpe.inovagovlab.decisoestce.model;


import jakarta.persistence.*;
@Entity
@Table(name = "dicionario")
public class Dicionario {

    @Id
    private String palavra;
    @Column(name = "significado", columnDefinition = "TEXT")
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

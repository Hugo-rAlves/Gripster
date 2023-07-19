package org.ufrpe.inovagovlab.decisoestce.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "recomendacao")
public class Recomendacao {
    @Id
    private String idProcesso;
    @Column
    private String nomeUjRecomendacao;
    @Column(columnDefinition="TEXT")
    private String textoOriginal;
    @Column(columnDefinition="TEXT")
    private String textoLimpo;
    @Column(columnDefinition="TEXT")
    private String sumario;

    public String getIdProcesso() {
        return idProcesso;
    }

    public void setIdProcesso(String idProcesso) {
        this.idProcesso = idProcesso;
    }

    public String getNomeUjRecomendacao() {
        return nomeUjRecomendacao;
    }

    public void setNomeUjRecomendacao(String nomeUjRecomendacao) {
        this.nomeUjRecomendacao = nomeUjRecomendacao;
    }

    public String getTextoOriginal() {
        return textoOriginal;
    }

    public void setTextoOriginal(String textoOriginal) {
        this.textoOriginal = textoOriginal;
    }

    public String getTextoLimpo() {
        return textoLimpo;
    }

    public void setTextoLimpo(String textoLimpo) {
        this.textoLimpo = textoLimpo;
    }

    public String getSumario() {
        return sumario;
    }

    public void setSumario(String sumario) {
        this.sumario = sumario;
    }
}

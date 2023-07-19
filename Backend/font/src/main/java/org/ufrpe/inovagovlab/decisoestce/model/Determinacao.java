package org.ufrpe.inovagovlab.decisoestce.model;

import jakarta.persistence.*;

@Entity
@Table(name = "determinacao")
public class Determinacao {
    @Id
    private String idProcesso;
    @Column
    private String nomeUjDeterminacao;
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

    public String getNomeUjDeterminacao() {
        return nomeUjDeterminacao;
    }

    public void setNomeUjDeterminacao(String nomeUjDeterminacao) {
        this.nomeUjDeterminacao = nomeUjDeterminacao;
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

    public String getSumario() { return sumario; }

    public void setSumario(String sumario) { this.sumario = sumario; }
}

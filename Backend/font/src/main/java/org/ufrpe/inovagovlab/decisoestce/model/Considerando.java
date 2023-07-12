package org.ufrpe.inovagovlab.decisoestce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "considerando")
public class Considerando {
    @Id
    private String idProcesso;
    @Column
    private String tipoConsiderando;
    @Column(length = 1000)
    private String pessoaConsiderando;
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

    public String getTipoConsiderando() {
        return tipoConsiderando;
    }

    public void setTipoConsiderando(String tipoConsiderando) {
        this.tipoConsiderando = tipoConsiderando;
    }

    public String getPessoaConsiderando() {
        return pessoaConsiderando;
    }

    public void setPessoaConsiderando(String pessoaConsiderando) {
        this.pessoaConsiderando = pessoaConsiderando;
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

    @Override
    public String toString() {
        return "Considerando{" +
                "idProcesso='" + idProcesso + '\'' +
                ", tipoConsiderando='" + tipoConsiderando + '\'' +
                ", pessoaConsiderando='" + pessoaConsiderando + '\'' +
                ", textoOriginal='" + textoOriginal + '\'' +
                ", textoLimpo='" + textoLimpo + '\'' +
                '}';
    }
}

package org.ufrpe.inovagovlab.decisoestce.model;

import com.sun.istack.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "processo")
public class Processo {
    @Id
    private String id;

    @Column
    private String nomeUjPrincipal;
    @Column
    private String esferaUj;
    @Column
    private String nomeMunicipioPrincipal;
    @Column
    private String nomePessoa;

    @Column
    private String nomeColegiado;
    @Column
    private String statusProcesso;
    @Column
    private int anoExercicio;
    @Column
    private String modalidade;

    @Column
    private LocalDate dataJulgamento;
    @Column
    private LocalTime horaJulgamento;
    @Column
    private int anoAcordaoParecer;

    @Column
    private String relator;
    @Column
    private String resultado;

    @Column
    private String tipo;
    @Column
    private String documento;
    @Column
    private String linkDocumento;
    @Column
    private String linkProcesso;

    @Column(columnDefinition = "TEXT")
    private String superSummary;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeUjPrincipal() {
        return nomeUjPrincipal;
    }

    public void setNomeUjPrincipal(String nomeUjPrincipal) {
        this.nomeUjPrincipal = nomeUjPrincipal;
    }

    public String getEsferaUj() {
        return esferaUj;
    }

    public void setEsferaUj(String esferaUj) {
        this.esferaUj = esferaUj;
    }

    public String getNomeMunicipioPrincipal() {
        return nomeMunicipioPrincipal;
    }

    public void setNomeMunicipioPrincipal(String nomeMunicipioPrincipal) {
        this.nomeMunicipioPrincipal = nomeMunicipioPrincipal;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }

    public String getNomeColegiado() {
        return nomeColegiado;
    }

    public void setNomeColegiado(String nomeColegiado) {
        this.nomeColegiado = nomeColegiado;
    }

    public String getStatusProcesso() {
        return statusProcesso;
    }

    public void setStatusProcesso(String statusProcesso) {
        this.statusProcesso = statusProcesso;
    }

    public int getAnoExercicio() {
        return anoExercicio;
    }

    public void setAnoExercicio(int anoExercicio) {
        this.anoExercicio = anoExercicio;
    }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }

    public LocalDate getDataJulgamento() {
        return dataJulgamento;
    }

    public void setDataJulgamento(LocalDate dataJulgamento) {
        this.dataJulgamento = dataJulgamento;
    }

    public LocalTime getHoraJulgamento() {
        return horaJulgamento;
    }

    public void setHoraJulgamento(LocalTime horaJulgamento) {
        this.horaJulgamento = horaJulgamento;
    }

    public int getAnoAcordaoParecer() {
        return anoAcordaoParecer;
    }

    public void setAnoAcordaoParecer(int anoAcordaoParecer) {
        this.anoAcordaoParecer = anoAcordaoParecer;
    }

    public String getRelator() {
        return relator;
    }

    public void setRelator(String relator) {
        this.relator = relator;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getLinkDocumento() {
        return linkDocumento;
    }

    public void setLinkDocumento(String linkDocumento) {
        this.linkDocumento = linkDocumento;
    }

    public String getLinkProcesso() {
        return linkProcesso;
    }

    public void setLinkProcesso(String linkProcesso) {
        this.linkProcesso = linkProcesso;
    }

    public String getSuperSummary() {
        return superSummary;
    }

    public void setSuperSummary(String superSummary) {
        this.superSummary = superSummary;
    }
}

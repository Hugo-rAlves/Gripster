package org.ufrpe.inovagovlab.decisoestce.model.dto;

import java.time.LocalDate;

public class InformationBoardDTO {

    private String numeroProcesso;
    private String instituicao;
    private String municipio;
    private String gestor;
    private String relator;
    private int anoGestao;
    private LocalDate dataDecisao;
    private String modalidade;
    private String tipo;
    private String resultado;
    private String linkDocumentoOriginal;
    private String linkAcompanharProcesso;
    private String status;

    public InformationBoardDTO(String numeroProcesso, String instituicao, String municipio, String gestor, String relator, int anoGestao, LocalDate dataDecisao, String modalidade, String tipo, String resultado, String linkDocumentoOriginal, String linkAcompanharProcesso, String status) {
        this.numeroProcesso = numeroProcesso;
        this.instituicao = instituicao;
        this.municipio = municipio;
        this.gestor = gestor;
        this.relator = relator;
        this.anoGestao = anoGestao;
        this.dataDecisao = dataDecisao;
        this.modalidade = modalidade;
        this.tipo = tipo;
        this.resultado = resultado;
        this.linkDocumentoOriginal = linkDocumentoOriginal;
        this.linkAcompanharProcesso = linkAcompanharProcesso;
        this.status = status;
    }

    public String getNumeroProcesso() {
        return numeroProcesso;
    }

    public void setNumeroProcesso(String numeroProcesso) {
        this.numeroProcesso = numeroProcesso;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getGestor() {
        return gestor;
    }

    public void setGestor(String gestor) {
        this.gestor = gestor;
    }

    public String getRelator() {
        return relator;
    }

    public void setRelator(String relator) {
        this.relator = relator;
    }

    public int getAnoGestao() {
        return anoGestao;
    }

    public void setAnoGestao(int anoGestao) {
        this.anoGestao = anoGestao;
    }

    public LocalDate getDataDecisao() {
        return dataDecisao;
    }

    public void setDataDecisao(LocalDate dataDecisao) {
        this.dataDecisao = dataDecisao;
    }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getLinkDocumentoOriginal() {
        return linkDocumentoOriginal;
    }

    public void setLinkDocumentoOriginal(String linkDocumentoOriginal) {
        this.linkDocumentoOriginal = linkDocumentoOriginal;
    }

    public String getLinkAcompanharProcesso() {
        return linkAcompanharProcesso;
    }

    public void setLinkAcompanharProcesso(String linkAcompanharProcesso) {
        this.linkAcompanharProcesso = linkAcompanharProcesso;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

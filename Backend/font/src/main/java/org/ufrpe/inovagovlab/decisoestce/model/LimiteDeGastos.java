package org.ufrpe.inovagovlab.decisoestce.model;


import javax.persistence.*;

@Entity
@IdClass(LimiteDeGastosPK.class)
@Table(name= "LimiteDeGastos")
public class LimiteDeGastos {


    @Id
    @Column(name = "nrProcesso")
    private String nrProcesso;
    @Column(name = "dsTextoDetalhado", columnDefinition = "TEXT")
    private String dsTextoDetalhado;
    @Column(name = "dsAreaAplicacao")
    private String tpAreaAplicacao;
    @Column(name = "dsTipoLimite", length = 400)
    private String dsTipoLimite;
    @Column(name = "tpLimite", length = 10)
    private String tpLimite;
    @Column(name = "tpVoto")
    private String tpVoto;
    @Column(name = "dsFundamentacaoLegal", length = 500)
    private String dsFundamentacaoLegal;
    @Column(name = "dsBaseCalculo", length = 350)
    private String dsBaseCalculo;
    @Column(name = "vlAplicacao")
    private Double VlAplicacao;
    @Column(name = "vlLimite")
    private Double vlLimite;
    @Column(name = "idProcesso") // necessário?
    private Long idProcesso;
    @Column(name = "inPercentual")
    private int inPercentual;
    @Id
    @Column(name = "idTipoLimite")
    private int idTipoLimite;
    @Column(name = "inCumprido") // 0 - não cumprido | 1 - cumprido
    private int inCumprido;


    public String getNrProcesso() {
        return nrProcesso;
    }

    public void setNrProcesso(String nrProcesso) {
        this.nrProcesso = nrProcesso;
    }

    public String getDsTextoDetalhado() {
        return dsTextoDetalhado;
    }

    public void setDsTextoDetalhado(String dsTextoDetalhado) {
        this.dsTextoDetalhado = dsTextoDetalhado;
    }

    public String getTpAreaAplicacao() {
        return tpAreaAplicacao;
    }

    public void setTpAreaAplicacao(String tpAreaAplicacao) {
        this.tpAreaAplicacao = tpAreaAplicacao;
    }

    public String getDsTipoLimite() {
        return dsTipoLimite;
    }

    public void setDsTipoLimite(String dsTipoLimite) {
        this.dsTipoLimite = dsTipoLimite;
    }

    public String getTpLimite() {
        return tpLimite;
    }

    public void setTpLimite(String tpLimite) {
        this.tpLimite = tpLimite;
    }

    public String getTpVoto() {
        return tpVoto;
    }

    public void setTpVoto(String tpVoto) {
        this.tpVoto = tpVoto;
    }

    public String getDsFundamentacaoLegal() {
        return dsFundamentacaoLegal;
    }

    public void setDsFundamentacaoLegal(String dsFundamentacaoLegal) {
        this.dsFundamentacaoLegal = dsFundamentacaoLegal;
    }

    public String getDsBaseCalculo() {
        return dsBaseCalculo;
    }

    public void setDsBaseCalculo(String dsBaseCalculo) {
        this.dsBaseCalculo = dsBaseCalculo;
    }

    public Double getVlAplicacao() {
        return VlAplicacao;
    }

    public void setVlAplicacao(Double vlAplicacao) {
        VlAplicacao = vlAplicacao;
    }

    public Double getVlLimite() {
        return vlLimite;
    }

    public void setVlLimite(Double vlLimite) {
        this.vlLimite = vlLimite;
    }

    public Long getIdProcesso() {
        return idProcesso;
    }

    public void setIdProcesso(Long idProcesso) {
        this.idProcesso = idProcesso;
    }

    public int getInPercentual() {
        return inPercentual;
    }

    public void setInPercentual(int inPercentual) {
        this.inPercentual = inPercentual;
    }

    public int getIdTipoLimite() {
        return idTipoLimite;
    }

    public void setIdTipoLimite(int idTipoLimite) {
        this.idTipoLimite = idTipoLimite;
    }

    public int getInCumprido() {
        return inCumprido;
    }

    public void setInCumprido(int inCumprido) {
        this.inCumprido = inCumprido;
    }

}

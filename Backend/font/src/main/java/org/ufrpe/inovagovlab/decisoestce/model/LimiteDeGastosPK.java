package org.ufrpe.inovagovlab.decisoestce.model;

import java.io.Serializable;

public class LimiteDeGastosPK implements Serializable {

    private String nrProcesso;
    private int idTipoLimite;

    public LimiteDeGastosPK(){

    }

    public LimiteDeGastosPK(String nrProcesso, int idTipoLimite){
        this.nrProcesso = nrProcesso;
        this.idTipoLimite = idTipoLimite;
    }

    public String getNrProcesso() {
        return nrProcesso;
    }

    public void setNrProcesso(String nrProcesso) {
        this.nrProcesso = nrProcesso;
    }

    public int getIdTipoLimite() {
        return idTipoLimite;
    }

    public void setIdTipoLimite(int idTipoLimite) {
        this.idTipoLimite = idTipoLimite;
    }

}

package org.ufrpe.inovagovlab.decisoestce.model.dto;

public class IndicadorDTO {

    private int idIndicador;
    private String indicador;
    private int resultado;


    public int getIdIndicador() {
        return idIndicador;
    }

    public void setIdIndicador(int idIndicador) {
        this.idIndicador = idIndicador;
    }

    public IndicadorDTO(int idIndicador, String indicador, int resultado) {
        this.idIndicador = idIndicador;
        this.indicador = indicador;
        this.resultado = resultado;
    }

    public String getIndicador() {
        return indicador;
    }

    public void setIndicador(String indicador) {
        this.indicador = indicador;
    }

    public int getResultado() {
        return resultado;
    }

    public void setResultado(int resultado) {
        this.resultado = resultado;
    }
}

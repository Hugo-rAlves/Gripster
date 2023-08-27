package org.ufrpe.inovagovlab.decisoestce.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodigoValorMuncipio {

    private String id;
    private double valor;

    public CodigoValorMuncipio(String id, double valor) {
        this.id = id;
        this.valor = valor;
    }
    public CodigoValorMuncipio(){

    }
}

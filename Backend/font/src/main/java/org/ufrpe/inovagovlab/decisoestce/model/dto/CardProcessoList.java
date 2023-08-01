package org.ufrpe.inovagovlab.decisoestce.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CardProcessoList {
    private String orgaoReferenteDecisao;
    private String cidade;
    private Integer anoEmJulgamento;
    private String resultadoDecisao;
    private LocalDate dataDecisao;
    private String idDecisao;
}

package org.ufrpe.inovagovlab.decisoestce.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode
public class CardProcessoList {
    private String orgaoReferenteDecisao;
    private String cidade;
    private Integer anoEmJulgamento;
    private String resultadoDecisao;
    private LocalDate dataDecisao;
    private String idDecisao;
    private String nomePessoa;
}

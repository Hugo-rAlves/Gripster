package org.ufrpe.inovagovlab.decisoestce.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataDecisao;
    private String idDecisao;
    private String nomePessoa;
}

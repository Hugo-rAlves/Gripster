package org.ufrpe.inovagovlab.decisoestce.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CardInformacoesGeraisDto {

    private String numeroProcesso;
    private String instituicao;
    private String municipio;
    private String gestor;
    private String RelatorDecisao;
    private int anoDaGestao;
    private LocalDate dataJulgamento;
    private String tipoOrgao;
    private String resultado;
    private String linkDocumentoOriginal;
    private String linkAcompanharProcesso;
    private String statusDecisao;

}

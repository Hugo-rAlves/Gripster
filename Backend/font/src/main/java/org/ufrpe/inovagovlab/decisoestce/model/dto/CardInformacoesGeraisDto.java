package org.ufrpe.inovagovlab.decisoestce.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataJulgamento;
    private String tipoOrgao;
    private String resultado;
    private String linkDocumentoOriginal;
    private String linkAcompanharProcesso;
    private String statusDecisao;

}

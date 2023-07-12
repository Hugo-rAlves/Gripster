package org.ufrpe.inovagovlab.decisoestce.mapper;

import org.springframework.stereotype.Component;
import org.ufrpe.inovagovlab.decisoestce.model.Recomendacao;

import java.util.Arrays;
import java.util.List;

@Component
public class RecomendacaoMapper {

    public List<String> formataTexto(Recomendacao recomendacao){
        //return Arrays.asList(recomendacao.getTextoLimpo().split(";"));
        return Arrays.asList(recomendacao.getTextoLimpo().split("#####"));
    }

    public List<String> formataTextoSumarios(Recomendacao recomendacao){
        //return Arrays.asList(recomendacao.getTextoLimpo().split(";"));
        return Arrays.asList(recomendacao.getSumario().split("#####"));
    }
}

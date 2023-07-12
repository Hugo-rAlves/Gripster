package org.ufrpe.inovagovlab.decisoestce.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;
import org.ufrpe.inovagovlab.decisoestce.model.LimiteDeGastos;
import org.ufrpe.inovagovlab.decisoestce.model.dto.IndicadorDTO;

import java.util.ArrayList;
import java.util.List;

@Component
public class LimiteDeGastosMapper {

    public LimiteDeGastos toEntity(JsonNode node) {
        LimiteDeGastos entity = new LimiteDeGastos();

        if (node.get("nr_processo") != null)
            entity.setNrProcesso(node.get("nr_processo").asText());
        if (node.get("ds_texto_detalhado") != null) {
            String textoDetalhado = node.get("ds_texto_detalhado").asText();
            entity.setDsTextoDetalhado(textoDetalhado);

        }
        if (node.get("tp_area_aplicacao") != null)
            entity.setTpAreaAplicacao(node.get("tp_area_aplicacao").asText());
        if (node.get("ds_tipo_limite") != null)
            entity.setDsTipoLimite(node.get("ds_tipo_limite").asText());
        if (node.get("tp_limite") != null)
            entity.setTpLimite(node.get("tp_limite").asText());
        if (node.get("tp_voto") != null)
            entity.setTpVoto(node.get("tp_voto").asText());
        if (node.get("ds_fundamentacao_legal") != null)
            entity.setDsFundamentacaoLegal(node.get("ds_fundamentacao_legal").asText());
        if (node.get("ds_base_calculo") != null)
            entity.setDsBaseCalculo(node.get("ds_base_calculo").asText());
        if (node.get("vl_aplicacao") != null)
            entity.setVlAplicacao(Double.parseDouble(node.get("vl_aplicacao").asText()));
        if (node.get("vl_limite") != null)
            entity.setVlLimite(Double.parseDouble(node.get("vl_limite").asText()));
        if (node.get("id_processo") != null)
            entity.setIdProcesso(Long.parseLong(node.get("id_processo").asText()));
        if (node.get("in_percentual") != null)
            entity.setInPercentual(Integer.parseInt(node.get("in_percentual").asText()));
        if (node.get("id_tipo_limite") != null)
            entity.setIdTipoLimite(Integer.parseInt(node.get("id_tipo_limite").asText()));
        if (node.get("in_cumprido") != null)
            entity.setInCumprido(Integer.parseInt(node.get("in_cumprido").asText()));
        return entity;
    }

    public List<IndicadorDTO> toDtoAll(List<LimiteDeGastos> entities){
        List<IndicadorDTO> indicadores = new ArrayList<>();
        for (LimiteDeGastos limiteDeGastos: entities) {
            indicadores.add(new IndicadorDTO(limiteDeGastos.getIdTipoLimite(), limiteDeGastos.getDsTipoLimite(),limiteDeGastos.getInCumprido()));
        }
        return indicadores;
    }




}

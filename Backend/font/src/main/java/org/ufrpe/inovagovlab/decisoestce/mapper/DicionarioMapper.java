package org.ufrpe.inovagovlab.decisoestce.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;
import org.ufrpe.inovagovlab.decisoestce.model.Dicionario;
import org.ufrpe.inovagovlab.decisoestce.model.dto.DicionarioDTO;

import java.util.ArrayList;
import java.util.List;

@Component
public class DicionarioMapper {

    public Dicionario toEntity(JSONObject node){
        Dicionario entity = new Dicionario();
        entity.setPalavra(node.get("key").toString());
        entity.setSignificado(node.get("value").toString());
        return entity;
    }

    public DicionarioDTO toDto(Dicionario entity){
        DicionarioDTO dto = new DicionarioDTO();
        dto.setPalavra(entity.getPalavra());
        dto.setSignificado(entity.getSignificado());
        return dto;
    }
    public List<DicionarioDTO> toDtoAll(List<Dicionario> entities){

        List<DicionarioDTO> dicionarioDTOS = new ArrayList<>();
        for (Dicionario dicionario : entities) {
            DicionarioDTO dto = new DicionarioDTO();
            dto.setPalavra(dicionario.getPalavra());
            dto.setSignificado(dicionario.getSignificado());
            dicionarioDTOS.add(dto);
        }
        return dicionarioDTOS;
    }
}

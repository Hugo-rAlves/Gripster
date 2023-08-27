package org.ufrpe.inovagovlab.decisoestce.mapper;

import org.springframework.stereotype.Component;
import org.ufrpe.inovagovlab.decisoestce.model.Processo;
import org.ufrpe.inovagovlab.decisoestce.model.dto.*;

import java.util.ArrayList;
import java.util.List;

@Component
public class MapperApi {
    public List<GestoresDTO> mapperNomesToGestoresDTO(List<String> gestores) {
        List<GestoresDTO> listaGestores = new ArrayList<>();
        for (String nomeGestor : gestores) {
            GestoresDTO dto = new GestoresDTO();
            dto.setName(nomeGestor);
            listaGestores.add(dto);
        }
        return listaGestores;
    }

    public List<CardProcessoList> mapProcessoToCardProcessoList(List<Processo> processosDeUmGestor) {
        List<CardProcessoList> processosDto = new ArrayList<>();
        for(Processo process:processosDeUmGestor){
            CardProcessoList cardDto = new CardProcessoList();
            cardDto.setCidade(process.getNomeMunicipioPrincipal());
            cardDto.setAnoEmJulgamento(process.getAnoExercicio());
            cardDto.setDataDecisao(process.getDataJulgamento());
            cardDto.setResultadoDecisao(process.getResultado());
            cardDto.setOrgaoReferenteDecisao(process.getNomeUjPrincipal());
            cardDto.setIdDecisao(process.getId());
            cardDto.setNomePessoa(process.getNomePessoa());
            processosDto.add(cardDto);

        }
        return processosDto;

    }

    public List<MunicipiosDto> mapperMunicipioToDto(List<String> municipiosDtos) {
        List<MunicipiosDto> dtos = new ArrayList<>();

        for(String local: municipiosDtos){
            MunicipiosDto dto = new MunicipiosDto();
            dto.setNomeMunicipio(local);
            dtos.add(dto);
        }
        return dtos;
    }

    public CardInformacoesGeraisDto mapperProcessoToInfGeralDto(Processo processo) {
        CardInformacoesGeraisDto dto = new CardInformacoesGeraisDto();

        dto.setNumeroProcesso(processo.getId());
        dto.setInstituicao(processo.getNomeUjPrincipal());
        dto.setMunicipio(processo.getNomeMunicipioPrincipal());
        dto.setGestor(processo.getNomePessoa());
        dto.setRelatorDecisao(processo.getRelator());
        dto.setAnoDaGestao(processo.getAnoExercicio());
        dto.setDataJulgamento(processo.getDataJulgamento());
        dto.setTipoOrgao(processo.getTipo());
        dto.setResultado(processo.getResultado());
        dto.setLinkDocumentoOriginal(processo.getLinkDocumento());
        dto.setLinkAcompanharProcesso(processo.getLinkProcesso());
        dto.setStatusDecisao(processo.getStatusProcesso());

        return dto;
    }

    public List<CodigoValorMuncipio> mapToMunicipiosValores(List<Object[]> query) {
        List<CodigoValorMuncipio> lista = new ArrayList<>();

        for (Object[] o :
                query) {
            CodigoValorMuncipio temp = new CodigoValorMuncipio();
            temp.setId(o[1].toString());
            temp.setValor(Double.parseDouble(o[2].toString()));
            lista.add(temp);
        }
        return lista;

    }
}

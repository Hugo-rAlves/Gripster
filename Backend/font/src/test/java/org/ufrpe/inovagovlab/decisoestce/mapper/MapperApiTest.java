package org.ufrpe.inovagovlab.decisoestce.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.ufrpe.inovagovlab.decisoestce.controller.ApiController;
import org.ufrpe.inovagovlab.decisoestce.model.Processo;
import org.ufrpe.inovagovlab.decisoestce.model.dto.CardInformacoesGeraisDto;
import org.ufrpe.inovagovlab.decisoestce.model.dto.CardProcessoList;
import org.ufrpe.inovagovlab.decisoestce.model.dto.GestoresDTO;
import org.ufrpe.inovagovlab.decisoestce.model.dto.MunicipiosDto;
import org.ufrpe.inovagovlab.decisoestce.repository.ProcessoRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = {MapperApi.class})
@ExtendWith(SpringExtension.class)
public class MapperApiTest {
    @Autowired
    private MapperApi mapperApi;


    @Test
    public void testMapperToGestoresDTO(){
        List<String> listaGestores = getGestoresSinteticos();

        List<GestoresDTO> gestoresExpected = new ArrayList<>();
        for (String gestorNome : listaGestores) {
            GestoresDTO dto = new GestoresDTO();
            dto.setName(gestorNome);
            gestoresExpected.add(dto);
        }
        List<GestoresDTO> gestoresMetodos = mapperApi.mapperNomesToGestoresDTO(listaGestores);

        assertEquals(gestoresExpected, gestoresMetodos);
    }
    @Test
    public void testMapProcessoToCardProcessoList(){
        List<CardProcessoList> processoListExpected = new ArrayList<>();
        List<Processo> processos = getProcessoSinteticos();

        for (Processo p :
                processos) {
            CardProcessoList dto = new CardProcessoList();
            dto.setNomePessoa(p.getNomePessoa());
            dto.setAnoEmJulgamento(p.getAnoExercicio());
            dto.setDataDecisao(p.getDataJulgamento());
            dto.setResultadoDecisao(p.getResultado());
            dto.setOrgaoReferenteDecisao(p.getNomeUjPrincipal());
            dto.setIdDecisao(p.getId());
            dto.setNomePessoa(p.getNomePessoa());

            processoListExpected.add(dto);
        }

        List<CardProcessoList> cardsMapper = mapperApi.mapProcessoToCardProcessoList(processos);
        assertEquals(processoListExpected, cardsMapper);

    }
    @Test
    public void testMunicipioToDto(){
        List<String> listaMunicipios = getMunicipiosSintetico();

        List<MunicipiosDto> dtos = new ArrayList<>();
        for (String mun :
                listaMunicipios) {
            MunicipiosDto m = new MunicipiosDto();
            m.setNomeMunicipio(mun);
            dtos.add(m);
        }
        List<MunicipiosDto> dtosMap = mapperApi.mapperMunicipioToDto(listaMunicipios);
        assertEquals(dtos, dtosMap);
    }
    @Test
    public void testCardInformacaoGeralDTO(){
        Processo p = getProcessoSinteticos().get(0);
        CardInformacoesGeraisDto cardExpected = new CardInformacoesGeraisDto();

        cardExpected.setNumeroProcesso(p.getId());
        cardExpected.setInstituicao(p.getNomeUjPrincipal());
        cardExpected.setMunicipio(p.getNomeMunicipioPrincipal());
        cardExpected.setGestor(p.getNomePessoa());
        cardExpected.setRelatorDecisao(p.getRelator());
        cardExpected.setAnoDaGestao(p.getAnoExercicio());
        cardExpected.setDataJulgamento(p.getDataJulgamento());
        cardExpected.setTipoOrgao(p.getTipo());
        cardExpected.setResultado(p.getResultado());
        cardExpected.setLinkAcompanharProcesso(p.getLinkProcesso());
        cardExpected.setLinkDocumentoOriginal(p.getLinkDocumento());
        cardExpected.setStatusDecisao(p.getStatusProcesso());

        CardInformacoesGeraisDto cardActual = mapperApi.mapperProcessoToInfGeralDto(p);
        assertEquals(cardExpected, cardActual);



    }
    private List<String> getMunicipiosSintetico() {
        List<String> lista = new ArrayList<>();
        lista.add("mun 1");
        lista.add("mun 2");
        lista.add("mun 3");
        lista.add("mun 4");
        return lista;

    }

    private List<Processo> getProcessoSinteticos(){
        List<Processo> processos = new ArrayList<>();

        Processo p = new Processo();

        p.setId("id");
        p.setNomeUjPrincipal("string teste");
        p.setEsferaUj("string teste");
        p.setNomePessoa("string teste");
        p.setNomeColegiado("string teste");
        p.setStatusProcesso("string teste");
        p.setAnoExercicio(2010);
        p.setModalidade("string teste");
        p.setDataJulgamento(LocalDate.now());
        p.setHoraJulgamento(LocalTime.now());
        p.setAnoAcordaoParecer(2011);
        p.setRelator("string teste");
        p.setResultado("string teste");
        p.setTipo("string teste");
        p.setDocumento("string teste");
        p.setLinkDocumento("string teste");
        p.setLinkProcesso("string teste");

        processos.add(p);

        return processos;
    }
    private List<String> getGestoresSinteticos(){
        List<String> listaGestores = new ArrayList<>();
        listaGestores.add("Primeiro gestor");
        listaGestores.add("Segundo gestor");
        listaGestores.add("Terceiro gestor");
        listaGestores.add("Quarto gestor");
        return listaGestores;
    }

}

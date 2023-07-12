package org.ufrpe.inovagovlab.decisoestce.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;
import org.ufrpe.inovagovlab.decisoestce.model.Processo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

@Component
public class ProcessoMapper {

    public Processo toEntity(JsonNode node) {
        Processo p = new Processo();
        p.setId(node.get("Processo").asText().trim());
        p.setNomeUjPrincipal(node.get("NomeUJPrincipalProcesso").asText().trim());
        p.setNomeColegiado(node.get("NomeColegiado").asText().trim());
        p.setStatusProcesso(node.get("StatusProcesso").asText().trim());
        p.setEsferaUj(node.get("EsferaUJPrincipalProcesso").asText().trim());
        p.setNomePessoa(node.get("NomePessoa").asText().trim());
        p.setNomeMunicipioPrincipal(node.get("MunicipioUJPrincipalProcesso").asText().trim());
        p.setAnoExercicio(node.get("ExercicioPrincipalProcesso").asInt());
        p.setModalidade(node.get("Modalidade").asText().trim());
        p.setRelator(node.get("NomeRelator").asText().trim());
        p.setResultado(node.get("Resultado").asText().trim());
        p.setTipo(node.get("Tipo").asText().trim());
        p.setLinkDocumento(node.get("LinkDocumento").asText().trim());
        p.setDocumento(node.get("Documento").asText().trim());
        p.setAnoAcordaoParecer(node.get("AnoAcordaoParecer").asInt());
        p.setLinkProcesso(node.get("LinkProcesso").asText().trim());

        p.setDataJulgamento(LocalDate.parse(node.get("DataSessaoJulgamento").asText().trim()));
        p.setHoraJulgamento(LocalTime.parse(node.get("HoraSessaoJulgamento").asText().trim()));

        return p;
    }

    public List<String> getUniquePessoas(List<Processo> processos){
        ArrayList<String> nomes = new ArrayList<>();
        for (Processo processo : processos) {
            if (!nomes.contains(processo.getNomePessoa())) {
                nomes.add(processo.getNomePessoa());
            }
        }
        Collections.sort(nomes);
        return nomes;
    }

    public List<String> getUniqueUj(List<Processo> processos){
        ArrayList<String> uj = new ArrayList<>();
        for (Processo processo : processos) {
            if (!uj.contains(processo.getNomeUjPrincipal())) {
                uj.add(processo.getNomeUjPrincipal());
            }
        }
        Collections.sort(uj);
        return uj;
    }

    public List<Processo> getRejeitados(List<Processo> processos){
        ArrayList<Processo> rejeitados = new ArrayList<>();
        for (Processo processo : processos) {
            if (processo.getStatusProcesso().contains("Rejeição")) {
                rejeitados.add(processo);
            }
        }
        return rejeitados;
    }

    public List<Processo> getAprovados(List<Processo> processos){
        ArrayList<Processo> aprovados = new ArrayList<>();
        for (Processo processo : processos) {
            if (processo.getStatusProcesso().contains("Aprovação") ) {
                aprovados.add(processo);
            }
        }
        return aprovados;
    }

    public List<String> getUniqueMunicipio(List<Processo> processos){
        ArrayList<String> municipio = new ArrayList<>();
        for (Processo processo : processos){
            if(!municipio.contains(processo.getNomeMunicipioPrincipal())){
                municipio.add(processo.getNomeMunicipioPrincipal());
            }
        }
        Collections.sort(municipio);
        return municipio;
    }
}

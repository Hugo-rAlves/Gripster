package org.ufrpe.inovagovlab.decisoestce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.ufrpe.inovagovlab.decisoestce.model.Processo;
import org.ufrpe.inovagovlab.decisoestce.model.Recomendacao;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecomendacaoRepository extends JpaRepository<Recomendacao, String> {
    @Query(value = "select sumario from recomendacao where id_processo = ?1 ;", nativeQuery = true)
    Optional<String> getSimplificacao(String id);
}

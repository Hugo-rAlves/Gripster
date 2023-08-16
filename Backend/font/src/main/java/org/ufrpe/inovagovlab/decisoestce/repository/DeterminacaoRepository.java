package org.ufrpe.inovagovlab.decisoestce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.ufrpe.inovagovlab.decisoestce.model.Determinacao;

import java.util.Optional;

@Repository
public interface DeterminacaoRepository extends JpaRepository<Determinacao, String> {
    @Query(value = "select sumario from determinacao where id_processo = ?1 ;", nativeQuery = true)
    Optional<String> getSimplificacao(String id);

    @Query(value = "select texto_limpo from determinacao where id_processo = ?1", nativeQuery = true)
    Optional<String> getTextoCompleto(String id);
}

package org.ufrpe.inovagovlab.decisoestce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.ufrpe.inovagovlab.decisoestce.model.LimiteDeGastos;

import java.util.List;

@Repository
public interface LimiteDeGastosRepository extends JpaRepository<LimiteDeGastos, String> {

    List<LimiteDeGastos> findByNrProcesso(String idProcesso);
    List<LimiteDeGastos> findByNrProcessoOrderByInCumpridoAsc(String idProcesso);

    @Query(value = "SELECT count(lg) FROM LimiteDeGastos lg where nr_processo = ?1 and in_cumprido = 0", nativeQuery = true)
    Integer countReprovacoes(String id);
    @Query(value = "SELECT lg.dsTipoLimite FROM LimiteDeGastos lg where nr_processo = ?1 and in_cumprido = 0", nativeQuery = true)
    List<String> findNaoCumpridos(String id);
}

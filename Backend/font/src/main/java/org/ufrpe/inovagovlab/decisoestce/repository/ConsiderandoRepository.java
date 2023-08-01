package org.ufrpe.inovagovlab.decisoestce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.ufrpe.inovagovlab.decisoestce.model.Considerando;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConsiderandoRepository extends JpaRepository<Considerando, String> {

    @Query("SELECT c FROM Considerando c WHERE c.sumario is null ORDER BY c.idProcesso")
    List<Considerando> considerandosSemSumario();

    @Query(value = "select sumario from considerando where id_processo = ?1 ;", nativeQuery = true)
    Optional<String> getSimplificacao(String id);
}

package org.ufrpe.inovagovlab.decisoestce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.ufrpe.inovagovlab.decisoestce.model.Considerando;

import java.util.List;

@Repository
public interface ConsiderandoRepository extends JpaRepository<Considerando, String> {

    @Query("SELECT c FROM Considerando c WHERE c.sumario is null ORDER BY c.idProcesso")
    List<Considerando> considerandosSemSumario();
}

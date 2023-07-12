package org.ufrpe.inovagovlab.decisoestce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ufrpe.inovagovlab.decisoestce.model.Determinacao;

@Repository
public interface DeterminacaoRepository extends JpaRepository<Determinacao, String> {
}

package org.ufrpe.inovagovlab.decisoestce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ufrpe.inovagovlab.decisoestce.model.Dicionario;

@Repository
public interface DicionarioRepository extends JpaRepository<Dicionario, String> {

    Dicionario findByPalavra(String palavra);
}

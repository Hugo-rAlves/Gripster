package org.ufrpe.inovagovlab.decisoestce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.ufrpe.inovagovlab.decisoestce.model.Processo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import java.util.List;

@Repository
public interface ProcessoRepository extends JpaRepository<Processo, String> {
    @Query("SELECT COUNT(DISTINCT nomeUjPrincipal) FROM Processo")
    long countDistinctNomeUjPrincipal();

    @Query("SELECT DISTINCT nomeUjPrincipal FROM Processo ORDER BY 1 ASC")
    List<String> distinctNomeUjPrincipal();

    @Query("SELECT DISTINCT nomeMunicipioPrincipal FROM Processo ORDER BY 1 ASC")
    List<String> distinctNomeMunicipioPrinciapl();

    @Query("SELECT COUNT(DISTINCT nomePessoa) FROM Processo")
    long countDistinctPessoa();

    @Query("SELECT DISTINCT nomePessoa FROM Processo ORDER BY 1 ASC")
    List<String> distinctPessoa();

    @Query("SELECT COUNT(p) FROM Processo p WHERE p.tipo = 'governo' and p.modalidade= 'prestação de contas'")
    long processoGovernoPrestacaoDeContasCount();

    @Query("SELECT DISTINCT p.nomePessoa FROM Processo p WHERE p.tipo = 'governo' and p.modalidade= 'prestação de contas'")
    List<String> pessoaDistinctGovernoPrestacaoDeContasCount();

    @Query("SELECT p FROM Processo p WHERE p.tipo = 'governo' and p.modalidade= 'prestação de contas'")
    List<Processo> getAllProcessos();

    @Query(value= "SELECT p FROM Processo p WHERE nome_pessoa = ?1 AND p.tipo = 'governo' and p.modalidade = 'prestação de contas'", nativeQuery = true)
    List<Processo> processosGovernoAndPrestacaoDeContasByNomePessoa(String pessoa);

    @Query(value= "SELECT p FROM Processo p WHERE nome_municipio_principal = ?1 AND p.tipo = 'governo' and p.modalidade = 'prestação de contas'", nativeQuery = true)
    List<Processo> processosGovernoAndPrestacaoDeContasByNomeMunicipioPrincipal(String municipio);

    List<Processo> findProcessosByNomeUjPrincipal(String uj);

    List<Processo> findProcessosByNomePessoa(String pessoa);

    List<Processo> findProcessosByNomeMunicipioPrincipal(String municipio);

    @Query("SELECT DISTINCT p FROM Processo p WHERE p.tipo = 'governo' and p.modalidade= 'prestação de contas'")
    List<Processo> findAllIdsOfPrestacaoDeContas();

    @Query(value = "SELECT * FROM processo WHERE tipo='governo' and modalidade ='prestação de contas' and data_julgamento between ?1 and ?2", nativeQuery = true)
    List<Processo> findByDataJulgamentoBetween(LocalDate dataInicial, LocalDate dataFinal);

    /*
    @Query("SELECT stock " +
            " FROM Stock stock " +
            " WHERE stock.name = :name AND stock.date = :date AND stock.id <> :id")
    Optional<Stock> findByStockUpdate(String name, LocalDate date, Long id);*/
}

package com.rd.autopecas.erp_autopecas.domain.compra;

import com.rd.autopecas.erp_autopecas.domain.compra.dto.CompraResumeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {

    @EntityGraph(attributePaths = "itemsCompra")
    Optional<Compra> findById(Long id);

    //n da pra usa entity graph com q
    @Query(value = """ 
    SELECT   
     c.id AS id,
     fu.id AS idFuncionario,
     fo.id AS idFornecedor,
     fp.id AS idFormaPagamento,
     c.status AS status,
     c.total_value AS totalValue
    FROM compra c
    INNER JOIN funcionario fu
    ON c.id_funcionario = fu.id
    INNER JOIN fornecedor fo
    ON c.id_fornecedor = fo.id
    LEFT JOIN forma_pagamento fp
    ON c.id_forma_pagamento = fp.id
    WHERE (:idFuncionario IS NULL OR c.id_funcionario = :idFuncionario)
    AND (:idFornecedor IS NULL OR c.id_fornecedor = :idFornecedor)
    AND (:idFormaPagamento IS NULL OR c.id_forma_pagamento = :idFormaPagamento)
    AND (:totalValueMin IS NULL OR c.total_value > :totalValueMin)
    AND (:totalValueMax IS NULL OR c.total_value < :totalValueMax)
    AND (:status IS NULL OR c.status LIKE %:status%)
    """,
            nativeQuery = true)
    Page<CompraResumeResponse> findWithFilters(Pageable pageable, @Param("idFuncionario") Long idFuncionario, @Param("idFornecedor") Long idFornecedor,
                                               @Param("idFormaPagamento") Long idFormaPagamento, @Param("totalValueMin")BigDecimal totalValueMin, @Param("totalValueMax")BigDecimal totalValueMax,
                                               @Param("status") String status);
}
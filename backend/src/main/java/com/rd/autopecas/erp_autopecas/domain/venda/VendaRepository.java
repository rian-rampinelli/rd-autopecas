package com.rd.autopecas.erp_autopecas.domain.venda;

import com.rd.autopecas.erp_autopecas.domain.venda.dto.VendaResumeResponse;
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
public interface VendaRepository extends JpaRepository<Venda, Long> {

    @EntityGraph(attributePaths = "ItemsVenda")
    Optional<Venda> findById(Long id);

    //n da pra usa entity graph com q
    @Query(value = """ 
    SELECT 
     v.id AS id,
     fu.id AS idFuncionario,
     cl.id AS idCliente,
     fp.id AS idFormaPagamento,
     v.status AS status,
     v.total_value AS totalValue
    FROM venda v
    INNER JOIN funcionario fu
    ON v.id_funcionario = fu.id
    INNER JOIN cliente cl
    ON v.id_cliente = cl.id
    LEFT JOIN forma_pagamento fp
    ON v.id_forma_pagamento = fp.id
    WHERE (:idFuncionario IS NULL OR v.id_funcionario = :idFuncionario)
    AND (:idCliente IS NULL OR v.id_cliente = :idCliente)
    AND (:idFormaPagamento IS NULL OR v.id_forma_pagamento = :idFormaPagamento)
    AND (:totalValueMin IS NULL OR v.total_value > :totalValueMin)
    AND (:totalValueMax IS NULL OR v.total_value < :totalValueMax)
    AND (:status IS NULL OR v.status LIKE %:status%)
    """,
            nativeQuery = true)
    Page<VendaResumeResponse> findWithFilters(Pageable pageable, @Param("idFuncionario") Long idFuncionario, @Param("idCliente") Long idCliente,
                                              @Param("idFormaPagamento") Long idFormaPagamento, @Param("totalValueMin") BigDecimal totalValueMin, @Param("totalValueMax") BigDecimal totalValueMax,
                                              @Param("status") String status);
}

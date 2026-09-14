package com.rd.autopecas.erp_autopecas.domain.estoque_item;

import com.rd.autopecas.erp_autopecas.domain.estoque_item.dto.EstoqueItemResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface EstoqueItemRepository extends JpaRepository<EstoqueItem, Long> {
    Optional<EstoqueItem> findByEstoque_IdAndItem_Id(Long estoqueId , Long itemId);

    //usando slq native
    @Query(value = """
    SELECT ei.id,e.id,i.id,i.nome,ei.quantidade,ei.localizacao
    FROM estoque_item ei
    INNER JOIN item i 
    ON ei.id_item = i.id
    INNER JOIN estoque e
    ON ei.id_estoque = e.id
    WHERE (:idEstoque IS NULL OR ei.id_estoque = :idEstoque)
    AND (:idItem IS NULL OR ei.id_item = :idItem)
    AND LOWER(i.nome) LIKE LOWER(CONCAT('%', :nomeItem, '%'))
    AND LOWER(ei.localizacao) LIKE LOWER(CONCAT('%', :localizacao, '%'))
    AND (:qtdMinima IS NULL OR ei.quantidade > :qtdMinima)
    AND (:qtdMaxima IS NULL OR ei.quantidade < :qtdMaxima)
    """,
            nativeQuery = true)
    public Page<EstoqueItemResponse> findWithFilters(Pageable pageable, @Param("idEstoque") Long idEstoque, @Param("idItem") Long idItem, @Param("nomeItem") String nomeItem,
                                                     @Param("localizacao") String localizacao, @Param("qtdMinima") BigDecimal qtdMinima, @Param("qtdMaxima") BigDecimal qtdMaxima);


}
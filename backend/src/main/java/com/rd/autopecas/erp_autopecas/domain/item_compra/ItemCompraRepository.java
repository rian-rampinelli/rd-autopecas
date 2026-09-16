package com.rd.autopecas.erp_autopecas.domain.item_compra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemCompraRepository extends JpaRepository<ItemCompra, Long> {
    Optional<ItemCompra> findByIdAndCompra_Id(Long idItemCompra, Long idCompra);
    Optional<ItemCompra> findByItem_IdAndCompra_IdAndEstoque_Id(Long idItem, Long idCompra,Long idEstoque);
}
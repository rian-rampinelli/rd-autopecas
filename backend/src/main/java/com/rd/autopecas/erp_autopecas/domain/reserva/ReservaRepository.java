package com.rd.autopecas.erp_autopecas.domain.reserva;

import com.rd.autopecas.erp_autopecas.domain.estoque_item.EstoqueItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    Optional<Reserva> findByVenda_IdAndEstoqueItem_Id(Long vendaId , Long EstoqueItemId);

}
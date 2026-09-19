package com.rd.autopecas.erp_autopecas.domain.reserva.dto;
import com.rd.autopecas.erp_autopecas.domain.reserva.Reserva;
import com.rd.autopecas.erp_autopecas.domain.venda.dto.VendaResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReservaResponse(
        Long id,
        Long idVenda,
        Long idEstoqueItem,
        BigDecimal quantidade,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ReservaResponse fromEntity(Reserva reserva) {
        return new ReservaResponse(
                reserva.getId(),
                reserva.getVenda().getId(),
                reserva.getEstoqueItem().getId(),
                reserva.getQuantidade(),
                reserva.getCreatedAt(),
                reserva.getUpdateAt()
        );
    }
}

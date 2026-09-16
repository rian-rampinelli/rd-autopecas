package com.rd.autopecas.erp_autopecas.domain.estoque.dto;

import java.math.BigDecimal;

public record EstoqueItemProjection(
        Long id,
        Long idItem,
        String nomeItem,
        BigDecimal quantidade,
        String localizacao
) {
}

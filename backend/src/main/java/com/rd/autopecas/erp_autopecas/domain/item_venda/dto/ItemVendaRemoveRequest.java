package com.rd.autopecas.erp_autopecas.domain.item_venda.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ItemVendaRemoveRequest(
        @NotNull
        Long id,
        @NotNull
        @DecimalMin(value = "0.01")
        BigDecimal quantidade

) {

}

package com.rd.autopecas.erp_autopecas.domain.venda.filter;

import java.math.BigDecimal;

public record VendaFilter(
        Long idFuncionario,
        Long idCliente,
        Long idFormaPagamento,
        BigDecimal totalValueMin,
        BigDecimal totalValueMax,
        String status

) {
}

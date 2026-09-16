package com.rd.autopecas.erp_autopecas.domain.compra.filter;

import java.math.BigDecimal;

public record CompraFilter(
        Long idFuncionario,
        Long idFornecedor,
        Long idFormaPagamento,
        BigDecimal totalValueMin,
        BigDecimal totalValueMax,
        String status

) {
}

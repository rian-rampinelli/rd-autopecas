package com.rd.autopecas.erp_autopecas.domain.venda.dto;

import java.math.BigDecimal;

public record VendaResumeResponse(
        Long id,
        Long idFuncionario,
        Long idCliente,
        Long idFormaPagamento,
        String status,
        BigDecimal totalValue

) {

}

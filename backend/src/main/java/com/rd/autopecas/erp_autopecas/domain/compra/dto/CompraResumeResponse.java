package com.rd.autopecas.erp_autopecas.domain.compra.dto;

import java.math.BigDecimal;



public record CompraResumeResponse(
        Long id,
        Long idFuncionario,
        Long idFornecedor,
        Long idEstoque,
        Long idFormaPagamento,
        String status,
        BigDecimal totalValue

) {

}

package com.rd.autopecas.erp_autopecas.domain.compra.dto;

import com.rd.autopecas.erp_autopecas.domain.common.StatusTransacao;
import com.rd.autopecas.erp_autopecas.domain.compra.Compra;
import com.rd.autopecas.erp_autopecas.domain.item_compra.dto.ItemCompraResponse;

import java.math.BigDecimal;
import java.util.List;


public record CompraResponse(
        Long id,
        Long idFuncionario,
        Long idFornecedor,
        StatusTransacao status,
        BigDecimal totalValue,
        List<ItemCompraResponse> itemns

) {
    public static CompraResponse fromEntity(Compra compra) {
        List<ItemCompraResponse> itemNaCompra = compra.getItemsCompra().stream()
                .map(itemCompra -> ItemCompraResponse.fromEntity(itemCompra))
                .toList();

        return new CompraResponse(
                compra.getId(),
                compra.getFuncionario().getId(),
                compra.getFornecedor().getId(),
                compra.getStatus(),
                compra.getTotalValue(),
                itemNaCompra
        );
    }
}

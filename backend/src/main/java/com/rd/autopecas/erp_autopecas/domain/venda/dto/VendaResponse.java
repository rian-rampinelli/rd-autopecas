package com.rd.autopecas.erp_autopecas.domain.venda.dto;

import com.rd.autopecas.erp_autopecas.domain.common.StatusTransacao;
import com.rd.autopecas.erp_autopecas.domain.venda.Venda;
import com.rd.autopecas.erp_autopecas.domain.item_venda.dto.ItemVendaResponse;
import java.math.BigDecimal;
import java.util.List;


public record VendaResponse(
        Long id,
        Long idFuncionario,
        Long idCliente,
        Long idFormaPagamento,
        StatusTransacao status,
        BigDecimal totalValue,
        List<ItemVendaResponse> itemns

) {
    public static VendaResponse fromEntity(Venda venda) {
        List<ItemVendaResponse> itemNaVenda = venda.getItemsVenda().stream()
                .map(itemVenda -> ItemVendaResponse.fromEntity(itemVenda))
                .toList();

        return new VendaResponse(
                venda.getId(),
                venda.getFuncionario().getId(),
                venda.getCliente().getId(),
                venda.getFormaPagamento() != null ? venda.getFormaPagamento().getId():null,
                venda.getStatus(),
                venda.getTotalValue(),
                itemNaVenda
        );
    }
}

package com.rd.autopecas.erp_autopecas.domain.item_venda.dto;

import com.rd.autopecas.erp_autopecas.domain.item_venda.ItemVenda;
import java.math.BigDecimal;

public record ItemVendaResponse(

        Long idItemVenda,
        Long idEstoque,
        Long idItem,
        Long idVenda,
        BigDecimal quantidade,
        BigDecimal itemValue

) {
    public static ItemVendaResponse fromEntity(ItemVenda itemVenda) {
        return new ItemVendaResponse(
                itemVenda.getId(),
                itemVenda.getEstoque() != null ? itemVenda.getEstoque().getId() : null,
                itemVenda.getItem().getId(),
                itemVenda.getVenda().getId(),
                itemVenda.getQuantidade(),
                itemVenda.getItemValue()
        );
    }


}

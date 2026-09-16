package com.rd.autopecas.erp_autopecas.domain.item_compra.dto;

import com.rd.autopecas.erp_autopecas.domain.item_compra.ItemCompra;
import java.math.BigDecimal;

public record ItemCompraResponse(
        Long idItemCompra,
        Long idItem,
        Long idCompra,
        Long idEstoque,
        BigDecimal quantidade,
        BigDecimal itemValue

) {
    public static ItemCompraResponse fromEntity(ItemCompra itemCompra) {
        return new ItemCompraResponse(
                itemCompra.getId(),
                itemCompra.getItem().getId(),
                itemCompra.getCompra().getId(),
                itemCompra.getEstoque() != null ? itemCompra.getEstoque().getId() : null,
                itemCompra.getQuantidade(),
                itemCompra.getItemValue()

        );
    }


}

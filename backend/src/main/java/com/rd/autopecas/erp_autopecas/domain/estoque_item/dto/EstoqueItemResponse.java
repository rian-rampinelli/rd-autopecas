package com.rd.autopecas.erp_autopecas.domain.estoque_item.dto;

import com.rd.autopecas.erp_autopecas.domain.estoque_item.EstoqueItem;

import java.math.BigDecimal;

public record EstoqueItemResponse(
        Long id,
        Long idEstoque,
        Long idItem,
        String nomeItem,
        BigDecimal quantidade,
        String localizacao


) {
    public static EstoqueItemResponse fromEntity(EstoqueItem estoqueItem) {
        return new EstoqueItemResponse(
                estoqueItem.getId(),
                estoqueItem.getEstoque().getId(),
                estoqueItem.getItem().getId(),
                estoqueItem.getItem().getNome(),
                estoqueItem.getQuantidadeDisponivel(),
                estoqueItem.getLocalizacao()
        );
    }
}

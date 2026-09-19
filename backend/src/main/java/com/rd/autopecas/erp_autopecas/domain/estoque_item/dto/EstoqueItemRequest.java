package com.rd.autopecas.erp_autopecas.domain.estoque_item.dto;
import com.rd.autopecas.erp_autopecas.domain.estoque_item.EstoqueItem;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record EstoqueItemRequest(
        @NotNull
        Long idItem,
        @NotNull
        @DecimalMin(value = "0.01")
        BigDecimal quantidade,
        String localizacao
) {
    public EstoqueItem toEntity(){
        EstoqueItem estoqueItem = new EstoqueItem();
        estoqueItem.setQuantidadeDisponivel(quantidade);
        estoqueItem.setLocalizacao(localizacao);
        return estoqueItem;
    }

}

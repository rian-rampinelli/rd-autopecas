package com.rd.autopecas.erp_autopecas.domain.item_compra;

import com.rd.autopecas.erp_autopecas.domain.Item.Item;
import com.rd.autopecas.erp_autopecas.domain.common.Auditable;
import com.rd.autopecas.erp_autopecas.domain.compra.Compra;
import com.rd.autopecas.erp_autopecas.domain.estoque.Estoque;
import com.rd.autopecas.erp_autopecas.domain.item_venda.ItemVenda;
import com.rd.autopecas.erp_autopecas.domain.venda.Venda;
import com.rd.autopecas.erp_autopecas.exceptions.ValidationException;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "item_compra")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ItemCompra extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_value",nullable = false,precision = 10,scale = 2)
    private BigDecimal itemValue;

    @Column(name = "quantidade",nullable = false,precision = 10,scale = 2)
    private BigDecimal quantidade;

    @ManyToOne
    @JoinColumn(name = "id_item", nullable = false)
    private Item item;

    @ManyToOne
    @JoinColumn(name = "id_estoque")
    private Estoque estoque;

    @ManyToOne
    @JoinColumn(name = "id_compra", nullable = false)
    private Compra compra;

    public void diminuirQuantidade(ItemCompra itemCompra,BigDecimal qtd){
        if(itemCompra.getQuantidade().compareTo(qtd) >=0){
            itemCompra.setQuantidade(itemCompra.getQuantidade().subtract(qtd));
        }
        else {
            throw new ValidationException("quantidade não existe para ser tirada");
        }

    }
}

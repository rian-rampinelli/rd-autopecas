package com.rd.autopecas.erp_autopecas.domain.item_venda;

import com.rd.autopecas.erp_autopecas.domain.Item.Item;
import com.rd.autopecas.erp_autopecas.domain.common.Auditable;
import com.rd.autopecas.erp_autopecas.domain.estoque.Estoque;
import com.rd.autopecas.erp_autopecas.domain.venda.Venda;
import com.rd.autopecas.erp_autopecas.exceptions.ValidationException;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "item_venda")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ItemVenda extends Auditable {

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
    @JoinColumn(name = "id_venda", nullable = false)
    private Venda venda;

    @ManyToOne
    @JoinColumn(name = "id_estoque")
    private Estoque estoque;

    public void diminuirQuantidade(BigDecimal qtd,ItemVenda itemVenda){
        if(itemVenda.getQuantidade().compareTo(qtd) >=0){
            itemVenda.setQuantidade(itemVenda.getQuantidade().subtract(qtd));
        }
        else {
            throw new ValidationException("quantidade não existe para ser tirada");
        }

    }

}

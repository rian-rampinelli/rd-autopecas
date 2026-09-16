package com.rd.autopecas.erp_autopecas.domain.venda;

import com.rd.autopecas.erp_autopecas.domain.cliente.Cliente;
import com.rd.autopecas.erp_autopecas.domain.common.Auditable;
import com.rd.autopecas.erp_autopecas.domain.common.StatusTransacao;
import com.rd.autopecas.erp_autopecas.domain.forma_pagamento.FormaPagamento;
import com.rd.autopecas.erp_autopecas.domain.funcionario.Funcionario;
import com.rd.autopecas.erp_autopecas.domain.item_compra.ItemCompra;
import com.rd.autopecas.erp_autopecas.domain.item_venda.ItemVenda;
import com.rd.autopecas.erp_autopecas.exceptions.ValidationException;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "venda")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Venda extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false,length = 128)
    private StatusTransacao status;

    @Column(name = "total_value",nullable = false,precision = 10,scale = 2)
    private BigDecimal totalValue;

    @ManyToOne
    @JoinColumn(name = "id_cliente",nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_funcionario",nullable = false)
    private Funcionario funcionario;

    @ManyToOne
    @JoinColumn(name = "id_forma_pagamento")
    private FormaPagamento formaPagamento;

    @OneToMany(mappedBy = "venda")
    @ToString.Exclude
    private List<ItemVenda> ItemsVenda = new ArrayList();


    public void addItemVenda(ItemVenda itemVenda) {
        ItemsVenda.add(itemVenda);
        itemVenda.setVenda(this);
    }

    public void removeItemVenda(ItemVenda itemVenda) {
        ItemsVenda.remove(itemVenda);
        itemVenda.setVenda(null);
    }

    public BigDecimal calcularTotal(){
        BigDecimal totalValue = BigDecimal.valueOf(0);
        for(ItemVenda itemVenda: getItemsVenda()){
            totalValue = totalValue.add(itemVenda.getQuantidade().multiply(itemVenda.getItemValue())
            );
        }
        setTotalValue(totalValue);
        return totalValue;
    }

    public void diminuirQuantidade(BigDecimal qtd,ItemVenda itemVenda){
        if(itemVenda.getQuantidade().compareTo(qtd) >0){
            itemVenda.setQuantidade(itemVenda.getQuantidade().subtract(qtd));
        }
        else {
            throw new ValidationException("quantidade não existe para ser tirada");
        }

    }
}

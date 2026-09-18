package com.rd.autopecas.erp_autopecas.domain.estoque_item;

import com.rd.autopecas.erp_autopecas.domain.Item.Item;
import com.rd.autopecas.erp_autopecas.domain.common.Auditable;
import com.rd.autopecas.erp_autopecas.domain.estoque.Estoque;
import com.rd.autopecas.erp_autopecas.domain.movimentacao_estoque.MovimentacaoEstoque;
import com.rd.autopecas.erp_autopecas.domain.reserva.Reserva;
import com.rd.autopecas.erp_autopecas.exceptions.ValidationException;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "estoque_item")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EstoqueItem extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantidade_disponivel",nullable = false,precision = 10,scale = 2)
    private BigDecimal quantidadeDisponivel;

    @Column(name = "quantidade_reservada",nullable = false,precision = 10,scale = 2)
    private BigDecimal quantidadeReservada;

    @Column(name = "localizacao", nullable = false, length = 255)
    private String localizacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_item", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estoque", nullable = false)
    private Estoque estoque;

    @OneToMany(mappedBy = "estoqueItem")
    @ToString.Exclude
    private List<MovimentacaoEstoque> movimentacoesEstoque = new ArrayList();

    @OneToMany(mappedBy = "estoqueItem")
    private List<Reserva> reservas = new ArrayList<>();

    public void addReserva(Reserva reserva){
        reservas.add(reserva);
        reserva.setEstoqueItem(this);
    }

    public void removeReserva(Reserva reserva){
        reservas.remove(reserva);
        reserva.setEstoqueItem(null);
    }

    public void addMovimentacao(MovimentacaoEstoque movimentacaoEstoque) {
        movimentacoesEstoque.add(movimentacaoEstoque);
        movimentacaoEstoque.setEstoqueItem(this);
    }

    public void removeMovimentacaoEstoque(MovimentacaoEstoque movimentacaoEstoque) {
        movimentacoesEstoque.remove(movimentacaoEstoque);
        movimentacaoEstoque.setEstoqueItem(null);
    }

    public void adicionarQuantidade(BigDecimal quantidadeAdd) {
        validaMaiorQueZero(quantidadeAdd);
        setQuantidadeDisponivel(quantidadeDisponivel.add(quantidadeAdd));
    }

    public void removerQuantidade(BigDecimal quantidadeRemove) {
        validaMaiorQueZero(quantidadeRemove);
        if (quantidadeRemove.compareTo(quantidadeDisponivel) > 0) {
            throw new ValidationException("Quantidade indisponível para retirar.");
        }

        setQuantidadeDisponivel(quantidadeDisponivel.subtract(quantidadeRemove));

    }

    private void validaMaiorQueZero(BigDecimal quantidade){
        if (quantidade.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Quantidade inválida. O valor deve ser maior que zero.");
        }
    }

}

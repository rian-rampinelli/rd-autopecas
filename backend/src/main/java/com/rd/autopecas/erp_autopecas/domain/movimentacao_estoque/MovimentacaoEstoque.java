package com.rd.autopecas.erp_autopecas.domain.movimentacao_estoque;

import com.rd.autopecas.erp_autopecas.domain.common.Auditable;
import com.rd.autopecas.erp_autopecas.domain.estoque_item.EstoqueItem;
import com.rd.autopecas.erp_autopecas.domain.movimentacao_estoque.enums.TypeMovimentacao;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "movimentacao_estoque")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MovimentacaoEstoque extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantidade",nullable = false,precision = 10,scale = 2)
    private BigDecimal quantidade;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_movimentacao", nullable = false, length = 255)
    private TypeMovimentacao typeMovimentacao;

    @ManyToOne
    @JoinColumn(name = "id_estoque_item", nullable = false)
    private EstoqueItem estoqueItem;


}

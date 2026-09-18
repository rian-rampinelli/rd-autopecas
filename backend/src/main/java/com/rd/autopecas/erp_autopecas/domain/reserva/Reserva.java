package com.rd.autopecas.erp_autopecas.domain.reserva;

import com.rd.autopecas.erp_autopecas.domain.common.Auditable;
import com.rd.autopecas.erp_autopecas.domain.estoque_item.EstoqueItem;
import com.rd.autopecas.erp_autopecas.domain.venda.Venda;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_reserva")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Reserva extends Auditable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_venda",nullable = false)
    private Venda venda;

    @ManyToOne
    @JoinColumn(name = "id_estoque_item",nullable = false)
    private EstoqueItem estoqueItem;

    @Column(name = "quantidade")
    private BigDecimal quantidade;
}

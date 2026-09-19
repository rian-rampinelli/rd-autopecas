package com.rd.autopecas.erp_autopecas.domain.movimentacao_estoque;

import com.rd.autopecas.erp_autopecas.domain.estoque_item.EstoqueItem;
import com.rd.autopecas.erp_autopecas.domain.movimentacao_estoque.enums.TypeMovimentacao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class MovimentacaoEstoqueService {

    private final MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;

    public MovimentacaoEstoque registrarTransacao( EstoqueItem estoqueItem,BigDecimal qtd, TypeMovimentacao typeMovimentacao) {
        MovimentacaoEstoque movimentacaoEstoque = new MovimentacaoEstoque();
        movimentacaoEstoque.setQuantidade(qtd);
        movimentacaoEstoque.setTypeMovimentacao(typeMovimentacao);
        estoqueItem.addMovimentacao(movimentacaoEstoque);
        movimentacaoEstoqueRepository.save(movimentacaoEstoque);
        return movimentacaoEstoque;
    }
}

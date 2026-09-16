package com.rd.autopecas.erp_autopecas.domain.estoque;

import com.rd.autopecas.erp_autopecas.domain.Item.Item;
import com.rd.autopecas.erp_autopecas.domain.Item.ItemRepository;
import com.rd.autopecas.erp_autopecas.domain.estoque_item.EstoqueItem;
import com.rd.autopecas.erp_autopecas.domain.estoque_item.EstoqueItemRepository;
import com.rd.autopecas.erp_autopecas.domain.estoque_item.dto.EstoqueItemResponse;
import com.rd.autopecas.erp_autopecas.domain.item_venda.ItemVenda;
import com.rd.autopecas.erp_autopecas.domain.movimentacao_estoque.MovimentacaoEstoque;
import com.rd.autopecas.erp_autopecas.domain.movimentacao_estoque.MovimentacaoEstoqueRepository;
import com.rd.autopecas.erp_autopecas.domain.movimentacao_estoque.enums.TypeMovimentacao;
import com.rd.autopecas.erp_autopecas.domain.unidade.Unidade;
import com.rd.autopecas.erp_autopecas.domain.unidade.UnidadeRepository;
import com.rd.autopecas.erp_autopecas.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
@AllArgsConstructor
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;
    private final EstoqueItemRepository estoqueItemRepository;
    private final UnidadeRepository unidadeRepository;
    private final ItemRepository itemRepository;
    private final MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;

    public void deleteById(Long id){
        //verificar se estoque ja foi utilizada em alguma compra ou venda
        findEntityEstoque(id);
        estoqueRepository.deleteById(id);
    }

    @Transactional
    public EstoqueItemResponse registrarEntrada(Estoque estoque,Long idItem,BigDecimal quantidade ,String localizacao){
        EstoqueItem estoqueItem = findByIdEstoqueAndItem(estoque.getId(),idItem);
        if(estoqueItem == null){
            Item item = findEntityItem(idItem);
            estoqueItem = new EstoqueItem();
            estoqueItem.setQuantidade(quantidade);
            estoqueItem.setLocalizacao(localizacao);
            estoque.addEstoqueItem(estoqueItem);
            estoqueItem.setItem(item);
        }
        else{
            estoqueItem.adicionarQuantidade(quantidade);
        }
        estoqueItemRepository.save(estoqueItem);
        registrarTransacao(quantidade,TypeMovimentacao.ENTRADA,estoqueItem);
        return EstoqueItemResponse.fromEntity(estoqueItem);
    }

    @Transactional
    public EstoqueItemResponse registrarSaida(ItemVenda itemVenda){
        EstoqueItem estoqueItem = findByIdEstoqueAndItem(itemVenda.getEstoque().getId(), itemVenda.getItem().getId());
        if(estoqueItem == null){
            throw new ResourceNotFoundException("nao existe esse item nesse estoque!");
        }
        //n uso save pois o hibernate ja gerencia com o @Transactional,fazendo um update no final
        estoqueItem.removerQuantidade(itemVenda.getQuantidade());
        registrarTransacao(itemVenda.getQuantidade(),TypeMovimentacao.SAIDA,estoqueItem);
        return EstoqueItemResponse.fromEntity(estoqueItem);
    }


    private MovimentacaoEstoque registrarTransacao(BigDecimal qtd, TypeMovimentacao typeMovimentacao, EstoqueItem estoqueItem) {
        MovimentacaoEstoque movimentacaoEstoque = new MovimentacaoEstoque();
        movimentacaoEstoque.setQuantidade(qtd);
        movimentacaoEstoque.setTypeMovimentacao(typeMovimentacao);
        estoqueItem.addMovimentacao(movimentacaoEstoque);
        movimentacaoEstoqueRepository.save(movimentacaoEstoque);
        return movimentacaoEstoque;
    }


    //helpers
    public Estoque findEntityEstoque(Long id){
        return estoqueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estoque não encontrado"));
    }

    public Unidade findEntityUnidade(Long id){
        return unidadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unidade não encontrado"));
    }

    public Item findEntityItem(Long id){
        return itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado"));
    }

    public EstoqueItem findByIdEstoqueAndItem(Long idEstoque,Long idItem){
        return estoqueItemRepository.findByEstoque_IdAndItem_Id(idEstoque,idItem)
                .orElse(null);
    }

    public EstoqueItem findEntityEstoqueItem(Long idEstoqueItem){
        return estoqueItemRepository.findById(idEstoqueItem)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado nesse estoque!"));
    }


}

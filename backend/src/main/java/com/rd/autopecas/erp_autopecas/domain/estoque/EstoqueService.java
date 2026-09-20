package com.rd.autopecas.erp_autopecas.domain.estoque;

import com.rd.autopecas.erp_autopecas.domain.Item.Item;
import com.rd.autopecas.erp_autopecas.domain.Item.ItemRepository;
import com.rd.autopecas.erp_autopecas.domain.estoque_item.EstoqueItem;
import com.rd.autopecas.erp_autopecas.domain.estoque_item.EstoqueItemRepository;
import com.rd.autopecas.erp_autopecas.domain.estoque_item.dto.EstoqueItemResponse;
import com.rd.autopecas.erp_autopecas.domain.item_compra.ItemCompra;
import com.rd.autopecas.erp_autopecas.domain.item_venda.ItemVenda;
import com.rd.autopecas.erp_autopecas.domain.movimentacao_estoque.MovimentacaoEstoqueService;
import com.rd.autopecas.erp_autopecas.domain.movimentacao_estoque.enums.TypeMovimentacao;
import com.rd.autopecas.erp_autopecas.domain.reserva.ReservaService;
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
    private final MovimentacaoEstoqueService movimentacaoEstoqueService;
    private final ReservaService reservaService;

    public void deleteById(Long id){
        //verificar se estoque ja foi utilizada em alguma compra ou venda
        findEntityEstoque(id);
        estoqueRepository.deleteById(id);
    }

    @Transactional
    public EstoqueItemResponse registrarEntrada(Estoque estoque, Item item, BigDecimal qtd,TypeMovimentacao tipoMovimentacao){
        EstoqueItem estoqueItem = findByIdEstoqueAndItem(estoque.getId(),item.getId());
        if(estoqueItem == null){
            estoqueItem = new EstoqueItem();
            estoqueItem.setQuantidadeDisponivel(qtd);
            estoqueItem.setLocalizacao(estoqueItem.getLocalizacao());
            estoqueItem.setEstoque(estoque);
            estoqueItem.setItem(item);
        }
        else{
            estoqueItem.adicionarQuantidade(qtd);
        }

        estoqueItemRepository.save(estoqueItem);
        movimentacaoEstoqueService.registrarTransacao(estoqueItem,qtd,tipoMovimentacao);
        return EstoqueItemResponse.fromEntity(estoqueItem);
    }

    @Transactional
    public EstoqueItemResponse registrarSaida(Estoque estoque,Item item,BigDecimal qtd){
        EstoqueItem estoqueItem = findByIdEstoqueAndItem(estoque.getId(),item.getId());

        if(estoqueItem == null){
            throw new ResourceNotFoundException("nao existe esse item nesse estoque!");
        }
        //n uso save pois o hibernate ja gerencia com o @Transactional,fazendo um update no final
        estoqueItem.removerQuantidade(qtd);
        movimentacaoEstoqueService.registrarTransacao(estoqueItem,qtd,TypeMovimentacao.SAIDA);
        return EstoqueItemResponse.fromEntity(estoqueItem);
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

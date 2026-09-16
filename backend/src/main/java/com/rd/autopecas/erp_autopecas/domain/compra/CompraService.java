package com.rd.autopecas.erp_autopecas.domain.compra;

import com.rd.autopecas.erp_autopecas.domain.Item.Item;
import com.rd.autopecas.erp_autopecas.domain.Item.ItemRepository;
import com.rd.autopecas.erp_autopecas.domain.common.StatusTransacao;
import com.rd.autopecas.erp_autopecas.domain.compra.dto.CompraRequest;
import com.rd.autopecas.erp_autopecas.domain.compra.dto.CompraResponse;
import com.rd.autopecas.erp_autopecas.domain.compra.dto.CompraResumeResponse;
import com.rd.autopecas.erp_autopecas.domain.compra.filter.CompraFilter;
import com.rd.autopecas.erp_autopecas.domain.estoque.Estoque;
import com.rd.autopecas.erp_autopecas.domain.estoque.EstoqueRepository;
import com.rd.autopecas.erp_autopecas.domain.estoque.EstoqueService;
import com.rd.autopecas.erp_autopecas.domain.forma_pagamento.FormaPagamento;
import com.rd.autopecas.erp_autopecas.domain.forma_pagamento.FormaPagamentoRepository;
import com.rd.autopecas.erp_autopecas.domain.fornecedor.Fornecedor;
import com.rd.autopecas.erp_autopecas.domain.fornecedor.FornecedorRepository;
import com.rd.autopecas.erp_autopecas.domain.funcionario.Funcionario;
import com.rd.autopecas.erp_autopecas.domain.funcionario.FuncionarioRepository;
import com.rd.autopecas.erp_autopecas.domain.item_compra.ItemCompra;
import com.rd.autopecas.erp_autopecas.domain.item_compra.ItemCompraRepository;
import com.rd.autopecas.erp_autopecas.domain.item_compra.dto.ItemCompraRequest;
import com.rd.autopecas.erp_autopecas.exceptions.ResourceNotFoundException;
import com.rd.autopecas.erp_autopecas.exceptions.ValidationException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
@AllArgsConstructor
@Slf4j
public class CompraService {
    
    private final CompraRepository compraRepository;
    private final FornecedorRepository fornecedorRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final FormaPagamentoRepository formaPagamentoRepository;
    private final ItemRepository itemRepository;
    private final ItemCompraRepository itemCompraRepository;
    private final EstoqueService estoqueService;
    private final EstoqueRepository estoqueRepository;



    public CompraResponse findById(Long id){
        Compra compra = findEntityCompra(id);
        return(CompraResponse.fromEntity(compra));
    }

    public Page<CompraResumeResponse> findAll(Pageable pageable, CompraFilter filter){
        String status = filter.status() == null ? null : filter.status().toUpperCase();
        return compraRepository.findWithFilters(pageable,filter.idFuncionario(),filter.idFornecedor(),filter.idEstoque(),
                filter.idFormaPagamento(),filter.totalValueMin(),filter.totalValueMax(),status);
    }

    @Transactional
    public CompraResponse gerarCompra(CompraRequest compraRequest) {
        Fornecedor fornecedor = findEntityFornecedor(compraRequest.idFornecedor());
        Funcionario funcionario = findEntityFuncionario(compraRequest.idFuncionario());
        funcionario.validarAtivo();
        Compra compra = new Compra();
        compra.setFornecedor(fornecedor);
        compra.setFuncionario(funcionario);
        compra.setStatus(StatusTransacao.EM_ANDAMENTO);
        compraRepository.save(compra);
        return CompraResponse.fromEntity(compra);
    }

    @Transactional
    public CompraResponse adicionarItemNaCompra(Long idCompra, ItemCompraRequest request){
        Compra compra = findEntityCompra(idCompra);
        verificaTransaçãoEmAndamento(compra);
        ItemCompra itemCompra = findEntityItemCompraByItemAndCompraAndEstoque(request.idItem(),idCompra,request.idEstoque());
        if(itemCompra == null){
            itemCompra = new ItemCompra();
            Item item = findEntityItem(request.idItem());
            Estoque estoque = findEntityEstoque(request.idEstoque());
            itemCompra.setQuantidade(request.quantidade());
            itemCompra.setItemValue(request.itemValue());
            itemCompra.setItem(item);
            itemCompra.setEstoque(estoque);
            compra.addItemCompra(itemCompra);
        }
        else{
            itemCompra.setQuantidade(itemCompra.getQuantidade().add(request.quantidade()));
        }
        recalcularTotal(compra);
        compraRepository.save(compra);
        return CompraResponse.fromEntity(compra);
    }

    @Transactional
    public CompraResponse removerItemDaCompra(Long idCompra,Long idItemCompra){
        Compra compra = findEntityCompra(idCompra);
        verificaTransaçãoEmAndamento(compra);
        ItemCompra itemCompra = findEntityItemCompraInCompra(idItemCompra,idCompra);
        compra.removeItemCompra(itemCompra);
        recalcularTotal(compra);
        compraRepository.save(compra);
        return CompraResponse.fromEntity(compra);
    }

    @Transactional
    public CompraResponse finalizarCompra(Long idCompra){
        Compra compra = findEntityCompra(idCompra);
        verificaTransaçãoPaga(compra);
        compra.setStatus(StatusTransacao.FINALIZADA);
        compraRepository.save(compra);
        return CompraResponse.fromEntity(compra);
    }

    @Transactional
    public CompraResponse processarPagamento(Long idCompra,Long idFormaDePagamento){
        Compra compra = findEntityCompra(idCompra);
        FormaPagamento formaPagamento = findEntityFormaPagamento(idFormaDePagamento);
        verificaTransaçãoEmAndamento(compra);
        compra.setStatus(StatusTransacao.AGUARDANDO_PAGAMENTO);
        log.info("pagamento foi aprovado");
        compra.setStatus(StatusTransacao.PAGA);
        compra.setFormaPagamento(formaPagamento);
        compraRepository.save(compra);
        return CompraResponse.fromEntity(compra);
    }

    @Transactional
    public CompraResponse registrarEntrega(Long idCompra){
        log.info("entrei na entrega");
        Compra compra = findEntityCompra(idCompra);
        verificaTransaçãoFinalizada(compra);
        registrarEntradaNoEstoque(compra);
        compra.setStatus(StatusTransacao.ENTREGUE);
        compraRepository.save(compra);
        return CompraResponse.fromEntity(compra);
    }

    public CompraResponse registrarCancelamento(Long idCompra){
        Compra compra = findEntityCompra(idCompra);
        verificaTransaçãoFinalizada(compra);
        compra.setStatus(StatusTransacao.CANCELADA);
        compraRepository.save(compra);
        return CompraResponse.fromEntity(compra);
    }


    public CompraResponse registrarAbandono(Long idCompra){
        log.info("entrei na abandono");
        Compra compra = findEntityCompra(idCompra);
        verificaTransaçãoEmAndamento(compra);
        compra.setStatus(StatusTransacao.ABANDONADA);
        compraRepository.save(compra);
        return CompraResponse.fromEntity(compra);
    }



    private void recalcularTotal(Compra compra){
        BigDecimal totalValue = compra.calcularTotal();
        compra.setTotalValue(totalValue);
    }

    private void registrarEntradaNoEstoque(Compra compra){
        for(ItemCompra itemCompra : compra.getItemsCompra()){
            estoqueService.registrarEntrada(itemCompra);
        }
    }




    //helpers
    private Compra findEntityCompra(Long id){
        return compraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("compra não encontrado"));
    }

    private Estoque findEntityEstoque(Long id){
        return estoqueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("estoque não encontrado"));
    }

    private Fornecedor findEntityFornecedor(Long id){
        return fornecedorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("fornecedor não encontrado"));
    }

    private Funcionario findEntityFuncionario(Long id){
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("funcionario não encontrado"));
    }

    private Item findEntityItem(Long id){
        return itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("item não encontrado"));
    }

    private ItemCompra findEntityItemCompraInCompra(Long idItemCompra,Long idCompra){
        return itemCompraRepository.findByIdAndCompra_Id(idItemCompra, idCompra)
                .orElseThrow(() -> new ResourceNotFoundException("Item não pertence à compra."));
    }

    private ItemCompra findEntityItemCompraByItemAndCompraAndEstoque(Long idItem,Long idCompra,Long idEstoque){
        return itemCompraRepository.findByItem_IdAndCompra_IdAndEstoque_Id(idItem, idCompra,idEstoque)
                .orElse(null);
    }


    private ItemCompra findEntityItemCompra(Long id){
        return itemCompraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("item na compra não encontrado"));
    }

    private FormaPagamento findEntityFormaPagamento(Long id){
        return formaPagamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FormaPagamento não encontrada"));
    }

    private void verificaTransaçãoEmAndamento(Compra compra){
        if(compra.getStatus() != StatusTransacao.EM_ANDAMENTO){
            throw new ValidationException("Transação não esta em andamento!");
        }
    }

    private void verificaTransaçãoPaga(Compra compra){
        if(compra.getStatus() != StatusTransacao.PAGA){
            throw new ValidationException("Transação precisa ser paga!");
        }
    }


    private void verificaTransaçãoFinalizada(Compra compra){
        if(compra.getStatus() != StatusTransacao.FINALIZADA){
            throw new ValidationException("Transação precisa estar finalizada!");
        }
    }


}

package com.rd.autopecas.erp_autopecas.domain.venda;

import com.rd.autopecas.erp_autopecas.domain.venda.dto.VendaRequest;
import com.rd.autopecas.erp_autopecas.domain.venda.dto.VendaResponse;
import com.rd.autopecas.erp_autopecas.domain.venda.dto.VendaResumeResponse;
import com.rd.autopecas.erp_autopecas.domain.venda.filter.VendaFilter;
import com.rd.autopecas.erp_autopecas.domain.item_venda.dto.ItemVendaRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("vendas")
public class VendaController {

    private final VendaService vendaService;

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    @GetMapping("{id}")
    public ResponseEntity<VendaResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok((vendaService.findById(id)));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    @GetMapping
    public ResponseEntity<Page<VendaResumeResponse>> findAll(Pageable pageable, @ModelAttribute @Valid VendaFilter vendaFilter) {
        return ResponseEntity.ok(vendaService.findAll(pageable,vendaFilter));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    @PostMapping
    public ResponseEntity<VendaResponse> gerarVenda(@RequestBody @Valid VendaRequest vendaRequest){
        return ResponseEntity.created(URI.create("/vendas")).body(vendaService.gerarVenda(vendaRequest));
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    @PostMapping("{id}")
    public ResponseEntity<VendaResponse> adicionarItemVenda(@PathVariable Long id, @RequestBody @Valid ItemVendaRequest itemVendaRequest){
        return ResponseEntity.created(URI.create("/vendas")).body(vendaService.adicionarItemNaVenda(id,itemVendaRequest));
    }

//    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'ESTOQUISTA')")
//    @DeleteMapping("{idVenda}/itemvenda/{idItemVenda}")
//    public ResponseEntity<VendaResponse> removerItemVenda(@PathVariable Long idVenda,@PathVariable Long idItemVenda){
//        return ResponseEntity.created(URI.create("/vendas")).body(vendaService.removerItemDaVenda(idVenda,idItemVenda));
//    }
//
//    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'ESTOQUISTA')")
//    @PostMapping("{idVenda}/processar_pagamento/{idFormaPagamento}")
//    public ResponseEntity<VendaResponse> processarPagamento(@PathVariable Long idVenda,@PathVariable Long idFormaPagamento){
//        return ResponseEntity.created(URI.create("/vendas")).body(vendaService.processarPagamento(idVenda,idFormaPagamento));
//    }
//    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'ESTOQUISTA')")
//    @PostMapping("{idVenda}/finalizar")
//    public ResponseEntity<VendaResponse> finalizarVenda(@PathVariable Long idVenda){
//        return ResponseEntity.created(URI.create("/vendas")).body(vendaService.finalizarVenda(idVenda));
//    }
//
//    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'ESTOQUISTA')")
//    @PostMapping("{idVenda}/entregar/estoque/{idEstoque}")
//    public ResponseEntity<VendaResponse> VendaEntregue(@PathVariable Long idVenda,@PathVariable Long idEstoque){
//        return ResponseEntity.created(URI.create("/vendas")).body(vendaService.registrarEntrega(idVenda,idEstoque));
//    }
//
//    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'ESTOQUISTA')")
//    @PostMapping("{idVenda}/cancelar")
//    public ResponseEntity<VendaResponse> cancelarVenda(@PathVariable Long idVenda){
//        return ResponseEntity.created(URI.create("/vendas")).body(vendaService.registrarCancelamento(idVenda));
//    }
//
//    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'ESTOQUISTA')")
//    @PostMapping("{idVenda}/abandonar")
//    public ResponseEntity<VendaResponse> registrarAbandono(@PathVariable Long idVenda){
//        return ResponseEntity.created(URI.create("/vendas")).body(vendaService.registrarAbandono(idVenda));
//    }


}

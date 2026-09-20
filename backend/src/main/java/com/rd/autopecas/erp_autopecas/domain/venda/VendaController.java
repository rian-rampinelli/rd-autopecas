package com.rd.autopecas.erp_autopecas.domain.venda;

import com.rd.autopecas.erp_autopecas.domain.item_venda.dto.ItemVendaRemoveRequest;
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
        return ResponseEntity.ok(vendaService.adicionarItemNaVenda(id,itemVendaRequest));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    @DeleteMapping("{idVenda}/removeritem")
    public ResponseEntity<VendaResponse> removerItemVenda(@PathVariable Long idVenda, @RequestBody @Valid ItemVendaRemoveRequest itemVendaRemoveRequest){
        return ResponseEntity.created(URI.create("/vendas")).body(vendaService.removerItemDaVenda(idVenda,itemVendaRemoveRequest));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    @PostMapping("{idVenda}/abandonar")
    public ResponseEntity<VendaResponse> registrarAbandono(@PathVariable Long idVenda){
        return ResponseEntity.created(URI.create("/vendas")).body(vendaService.registrarAbandono(idVenda));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    @PostMapping("{idVenda}/processar_pagamento/{idFormaPagamento}")
    public ResponseEntity<VendaResponse> processarPagamento(@PathVariable Long idVenda,@PathVariable Long idFormaPagamento){
       return ResponseEntity.ok(vendaService.processarPagamento(idVenda,idFormaPagamento));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    @PostMapping("{idVenda}/finalizar")
    public ResponseEntity<VendaResponse> finalizarVenda(@PathVariable Long idVenda){
        return ResponseEntity.ok(vendaService.finalizarVenda(idVenda));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    @PostMapping("{idVenda}/entregar")
    public ResponseEntity<VendaResponse> VendaEntregue(@PathVariable Long idVenda){
        return ResponseEntity.ok(vendaService.registrarEntrega(idVenda));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    @PostMapping("{idVenda}/cancelar")
    public ResponseEntity<VendaResponse> cancelarVenda(@PathVariable Long idVenda){
        return ResponseEntity.ok(vendaService.registrarCancelamento(idVenda));
    }

}

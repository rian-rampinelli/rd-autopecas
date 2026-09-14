package com.rd.autopecas.erp_autopecas.domain.compra;

import com.rd.autopecas.erp_autopecas.domain.compra.dto.CompraRequest;
import com.rd.autopecas.erp_autopecas.domain.compra.dto.CompraResponse;
import com.rd.autopecas.erp_autopecas.domain.compra.dto.CompraResumeResponse;
import com.rd.autopecas.erp_autopecas.domain.compra.filter.CompraFilter;
import com.rd.autopecas.erp_autopecas.domain.item_compra.dto.ItemCompraRequest;
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
@RequestMapping("compras")
public class CompraController {

    private final CompraService compraService;

    //busca de compras
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'ESTOQUISTA','VENDEDOR')")
    @GetMapping("{id}")
    public ResponseEntity<CompraResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok((compraService.findById(id)));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'ESTOQUISTA','VENDEDOR')")
    @GetMapping
    public ResponseEntity<Page<CompraResumeResponse>> findAll(Pageable pageable, @ModelAttribute @Valid CompraFilter compraFilter) {
        return ResponseEntity.ok(compraService.findAll(pageable,compraFilter));
    }

    //fluxo de compras
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'ESTOQUISTA')")
    @PostMapping
    public ResponseEntity<CompraResponse> gerarCompra(@RequestBody @Valid CompraRequest compraRequest){
        return ResponseEntity.created(URI.create("/compras")).body(compraService.gerarCompra(compraRequest));
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'ESTOQUISTA')")
    @PostMapping("{id}")
    public ResponseEntity<CompraResponse> adicionarItemCompra(@PathVariable Long id, @RequestBody @Valid ItemCompraRequest itemCompraRequest){
        return ResponseEntity.created(URI.create("/compras")).body(compraService.adicionarItemNaCompra(id,itemCompraRequest));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'ESTOQUISTA')")
    @DeleteMapping("{idCompra}/itemcompra/{idItemCompra}")
    public ResponseEntity<CompraResponse> removerItemCompra(@PathVariable Long idCompra,@PathVariable Long idItemCompra){
        return ResponseEntity.created(URI.create("/compras")).body(compraService.removerItemDaCompra(idCompra,idItemCompra));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'ESTOQUISTA')")
    @PostMapping("{idCompra}/processar_pagamento/{idFormaPagamento}")
    public ResponseEntity<CompraResponse> processarPagamento(@PathVariable Long idCompra,@PathVariable Long idFormaPagamento){
        return ResponseEntity.created(URI.create("/compras")).body(compraService.processarPagamento(idCompra,idFormaPagamento));
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'ESTOQUISTA')")
    @PostMapping("{idCompra}/finalizar")
    public ResponseEntity<CompraResponse> finalizarCompra(@PathVariable Long idCompra){
        return ResponseEntity.created(URI.create("/compras")).body(compraService.finalizarCompra(idCompra));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'ESTOQUISTA')")
    @PostMapping("{idCompra}/entregar")
    public ResponseEntity<CompraResponse> entregarCompra(@PathVariable Long idCompra){
        return ResponseEntity.created(URI.create("/compras")).body(compraService.registrarEntrega(idCompra));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'ESTOQUISTA')")
    @PostMapping("{idCompra}/cancelar")
    public ResponseEntity<CompraResponse> cancelarCompra(@PathVariable Long idCompra){
        return ResponseEntity.created(URI.create("/compras")).body(compraService.registrarCancelamento(idCompra));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'ESTOQUISTA')")
    @PostMapping("{idCompra}/abandonar")
    public ResponseEntity<CompraResponse> registrarAbandono(@PathVariable Long idCompra){
        return ResponseEntity.created(URI.create("/compras")).body(compraService.registrarAbandono(idCompra));
    }


}

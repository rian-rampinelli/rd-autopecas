package com.rd.autopecas.erp_autopecas.domain.estoque;



import com.rd.autopecas.erp_autopecas.domain.estoque.dto.EstoqueResponse;
import com.rd.autopecas.erp_autopecas.domain.estoque_item.dto.EstoqueItemRequest;
import com.rd.autopecas.erp_autopecas.domain.estoque_item.dto.EstoqueItemResponse;
import com.rd.autopecas.erp_autopecas.domain.movimentacao_estoque.dto.MovimentacaoEstoqueResponse;
import com.rd.autopecas.erp_autopecas.domain.movimentacao_estoque.filter.MovimentacaoEstoqueFilter;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/estoques")
@AllArgsConstructor
public class EstoqueController {
    
    private final EstoqueService estoqueService;
    private final EstoqueBuscaService estoqueBuscaService;
    
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'ESTOQUISTA','VENDEDOR')")
    @GetMapping("/{id}")
    public ResponseEntity<EstoqueResponse> findById(@PathVariable @Valid Long id) {
        return ResponseEntity.ok(estoqueBuscaService.findById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        estoqueService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'ESTOQUISTA','VENDEDOR')")
    @GetMapping("/{idEstoque}/movimentacoes")
    public ResponseEntity<List<MovimentacaoEstoqueResponse>> buscarHistoricoMovimetacoes(@PathVariable  Long idEstoque,@ModelAttribute @Valid MovimentacaoEstoqueFilter filter) {
        return ResponseEntity.ok(estoqueBuscaService.buscarHistoricoMovimentacoesDeEstoque(idEstoque,filter));

    }

}

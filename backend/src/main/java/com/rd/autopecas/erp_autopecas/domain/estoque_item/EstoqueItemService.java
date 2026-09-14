package com.rd.autopecas.erp_autopecas.domain.estoque_item;

import com.rd.autopecas.erp_autopecas.domain.estoque.EstoqueService;
import com.rd.autopecas.erp_autopecas.domain.estoque_item.dto.EstoqueItemResponse;
import com.rd.autopecas.erp_autopecas.domain.estoque_item.filter.EstoqueItemFilter;
import com.rd.autopecas.erp_autopecas.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



@Service
@AllArgsConstructor
@Slf4j
public class EstoqueItemService {

    private final EstoqueService estoqueService;
    private final EstoqueItemRepository estoqueItemRepository;

    public EstoqueItemResponse findByid(Long id){
        EstoqueItem estoqueItem = findEntityEstoqueItem(id);
        return EstoqueItemResponse.fromEntity(estoqueItem);
    }



    public Page<EstoqueItemResponse> findAll(Pageable pageable, EstoqueItemFilter filter){
        log.info("entrei no find all");
        Page<EstoqueItemResponse> estoqueItems =  estoqueItemRepository.findWithFilters(pageable,filter.idEstoque(),filter.idItem(),filter.nomeItem(),
                filter.localizacao(),filter.qtdMinima(),filter.qtdMaxima());
        return estoqueItems;

    }

    //helpers
    public EstoqueItem findEntityEstoqueItem(Long id){
        return estoqueItemRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("nao encontrado"));

    }
}

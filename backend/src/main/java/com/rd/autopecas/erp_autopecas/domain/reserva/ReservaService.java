package com.rd.autopecas.erp_autopecas.domain.reserva;

import com.rd.autopecas.erp_autopecas.domain.estoque_item.EstoqueItem;
import com.rd.autopecas.erp_autopecas.domain.estoque_item.EstoqueItemRepository;
import com.rd.autopecas.erp_autopecas.domain.item_venda.ItemVenda;
import com.rd.autopecas.erp_autopecas.domain.item_venda.ItemVendaRepository;
import com.rd.autopecas.erp_autopecas.domain.reserva.dto.ReservaResponse;
import com.rd.autopecas.erp_autopecas.domain.venda.Venda;
import com.rd.autopecas.erp_autopecas.domain.venda.VendaRepository;
import com.rd.autopecas.erp_autopecas.exceptions.ResourceNotFoundException;
import com.rd.autopecas.erp_autopecas.exceptions.ValidationException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@AllArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final VendaRepository vendaRepository;
    private final EstoqueItemRepository estoqueItemRepository;
    private final ItemVendaRepository itemVendaRepository;

    @Transactional
    public ReservaResponse adicionarReserva(EstoqueItem estoqueItem,ItemVenda itemVenda,BigDecimal qtdAumentar){
        log.info("entei em add reserva de reserva");
        verificaQuantidadeDisponivelNoEstoque(itemVenda,estoqueItem,qtdAumentar);
        Reserva reserva = findEntityReserva(itemVenda.getVenda().getId(),estoqueItem.getId());
        if(reserva == null){
            reserva = new Reserva();
            reserva.setVenda(itemVenda.getVenda());
            reserva.setEstoqueItem(estoqueItem);
            reserva.setQuantidade(qtdAumentar);
        }
        else {
            reserva.setQuantidade(reserva.getQuantidade().add(qtdAumentar));
        }

        tranfereQuantidadeDisponivelParaReserva(estoqueItem,itemVenda,qtdAumentar);
        log.info("irei salvar : reserva-adicionar reserva");
        estoqueItemRepository.save(estoqueItem);
        reservaRepository.save(reserva);
        return ReservaResponse.fromEntity(reserva);
    }

    public ReservaResponse removerReserva(EstoqueItem estoqueItem,ItemVenda itemVenda, BigDecimal qtdRetirar){
        log.info("entei em remove reserva de reserva");
        Reserva reserva = findEntityReserva(itemVenda.getVenda().getId(),estoqueItem.getId());
        retornaQuantidadeReservadaParaDisponivel(estoqueItem,reserva,qtdRetirar);
        log.info("irei bancar: rmover-rserva reserva");
        if(reserva.getQuantidade().compareTo(BigDecimal.ZERO) == 0){
            reservaRepository.delete(reserva);
        }
        else{
            reservaRepository.save(reserva);
        }
        estoqueItemRepository.save(estoqueItem);
        return ReservaResponse.fromEntity(reserva);

    }

    public void tranfereQuantidadeDisponivelParaReserva(EstoqueItem estoqueItem,ItemVenda itemVenda,BigDecimal qtdAdicionar){
        estoqueItem.setQuantidadeReservada(itemVenda.getQuantidade());
        estoqueItem.setQuantidadeDisponivel(estoqueItem.getQuantidadeDisponivel().subtract(qtdAdicionar));
    }

    public void retornaQuantidadeReservadaParaDisponivel(EstoqueItem estoqueItem,Reserva reserva,BigDecimal qtdRetirar){
        reserva.setQuantidade(reserva.getQuantidade().subtract(qtdRetirar));
        estoqueItem.setQuantidadeDisponivel(estoqueItem.getQuantidadeDisponivel().add(qtdRetirar));
        estoqueItem.setQuantidadeReservada(estoqueItem.getQuantidadeReservada().subtract(qtdRetirar));
    }

    public void verificaQuantidadeDisponivelNoEstoque(ItemVenda itemVenda,EstoqueItem estoqueItem,BigDecimal qtdAdicionar){
        if(qtdAdicionar.compareTo(estoqueItem.getQuantidadeDisponivel()) > 0){
            throw new ValidationException("quantidade insuficiente disponivel no estoque");
        }
    }

    //helpers
    private Venda findEntityVenda(Long id){
        return vendaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("venda não encontrado!"));
    }

    private ItemVenda findEntityItemVendaByItemAndVendaAndEstoque(Long idItem, Long idVenda,Long idEstoque){
        return itemVendaRepository.findByItem_IdAndVenda_IdAndEstoque_id(idItem, idVenda,idEstoque)
                .orElse(null);
    }

    private ItemVenda findEntityItemVendaInVenda(Long idItemVenda,Long idVenda){
        return itemVendaRepository.findByIdAndVenda_Id(idItemVenda, idVenda)
                .orElseThrow(() -> new ResourceNotFoundException("Item não pertence à essa venda!."));
    }

    private EstoqueItem findEntityEstoqueItem(Long idEstoque, Long idItem){
        return estoqueItemRepository.findByEstoque_IdAndItem_Id(idEstoque, idItem)
                .orElseThrow(() -> new ResourceNotFoundException("Item não pertence à essa estoque!."));
    }

    private Reserva findEntityReserva(Long idVenda, Long idEstoqueItem){
        return reservaRepository.findByVenda_IdAndEstoqueItem_Id(idVenda,idEstoqueItem)
                .orElse(null);
    }


}

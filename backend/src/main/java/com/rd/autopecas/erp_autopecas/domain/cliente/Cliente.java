package com.rd.autopecas.erp_autopecas.domain.cliente;

import com.rd.autopecas.erp_autopecas.domain.common.Auditable;
import com.rd.autopecas.erp_autopecas.domain.common.StatusCommon;
import com.rd.autopecas.erp_autopecas.domain.endereco_cliente.EnderecoCliente;
import com.rd.autopecas.erp_autopecas.domain.venda.Venda;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Cliente  extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "email", nullable = false, unique = true, length = 128)
    private String email;

    @Column(name = "numero", nullable = false, unique = true, length = 64)
    private String numero;

    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    private String cpf;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusCommon status;

    @OneToMany(mappedBy = "cliente")
    @ToString.Exclude
    private List<Venda> vendas = new ArrayList<>();


    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<EnderecoCliente> enderecoClientes = new ArrayList<>();



    public void addEndereco(EnderecoCliente enderecoCliente) {
        if(enderecoCliente == null){
            throw new IllegalArgumentException("Endereço não pode ser nulo");
        }

        enderecoClientes.add(enderecoCliente);
        enderecoCliente.setCliente(this);
    }

    public void removeEndereco(EnderecoCliente enderecoCliente) {
        enderecoClientes.remove(enderecoCliente);
        enderecoCliente.setCliente(null);
    }


    public void addVenda(Venda venda) {
        vendas.add(venda);
        venda.setCliente(this);
    }

    public void removeVenda(Venda venda) {
        vendas.remove(venda);
        venda.setCliente(null);
    }

    public boolean idadeValida(int idade){
        return idade >= 18 && idade <= 60;
    }
}

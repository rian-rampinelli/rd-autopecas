package com.rd.autopecas.erp_autopecas.funcional;

import com.rd.autopecas.erp_autopecas.domain.cliente.Cliente;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClienteTest {

    @Test
    void devoAceitarIdadeValida(){
        Cliente cliente = new Cliente();
        assertTrue(cliente.idadeValida(20));
    }
    @Test
    void devoRecusarIdadeValida(){
        Cliente cliente = new Cliente();
        assertFalse(cliente.idadeValida(15));
    }
}

package br.com.fiap.helps;

import br.com.fiap.domain.entities.Cliente;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public abstract class ValidarCliente {

    public static void verificarCliente(Cliente cliente, Optional<Cliente> clienteRetorno) {
        var cliente2 = clienteRetorno.orElseThrow(() -> new IllegalArgumentException("Cliente Vazio"));
        verifica(cliente,cliente2);
    }

    public static void verificarCliente(Cliente cliente, Cliente clienteRetorno) {
            verifica(cliente,clienteRetorno);
    }

    private static void verifica(Cliente cliente, Cliente clienteRetorno){
        assertThat(clienteRetorno.getCpf())
                .isNotNull()
                .isEqualTo(cliente.getCpf());

        assertThat(clienteRetorno.getEmail())
                .isNotNull()
                .isEqualTo(cliente.getEmail());

        assertThat(clienteRetorno.getNome())
                .isNotNull()
                .isEqualTo(cliente.getNome());

    }


}

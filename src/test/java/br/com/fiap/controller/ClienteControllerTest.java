package br.com.fiap.controller;


import br.com.fiap.domain.entities.Cliente;
import br.com.fiap.domain.services.ClienteService;
import br.com.fiap.exception.ResultadoVazioErro;
import br.com.fiap.helps.CriarCliente;
import br.com.fiap.rest.ClienteRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClienteControllerTest {

    @Mock
    ClienteService service;
    ClienteController controller;
    AutoCloseable openMocks;


    @BeforeEach
    void init() {
        openMocks = MockitoAnnotations.openMocks(this);
        controller = new ClienteController(service);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void testarSeSalvaCliente() {
        var cliente = new ClienteRequest(
                CriarCliente.gerarNome(),
                CriarCliente.gerarCPF(),
                CriarCliente.gerarEmail());

        doNothing().when(service).casdastrarCliente(any(Cliente.class));

        controller.salvarCliente(cliente);

        verify(service,times(1))
                .casdastrarCliente(any(Cliente.class));
    }

    @Test
    void testeSeSelocionarClientePeloCPFCorretamente(){
        when(service.pegarCPF(anyString()))
                .thenReturn(Optional.of(CriarCliente.criarCliente()));

        var cliente =  controller.pegarClientePeloCPF("123123");

        assertThat(cliente)
                .isNotNull()
                .isInstanceOf(Cliente.class);

        verify(service,times(1)).pegarCPF(anyString());


    }

    @Test
    void testeSeSelocionarClientePeloCPFVazio(){
        when(service.pegarCPF(anyString()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> controller.pegarClientePeloCPF("123123"))
                .hasMessage("Cliente não encontrado")
                        .isInstanceOf(ResultadoVazioErro.class);

        verify(service,times(1)).pegarCPF(anyString());

    }

    @Test
    void testeSeSelocionarClientePeloIDCorretamente(){
        when(service.pegarID(anyLong()))
                .thenReturn(Optional.of(CriarCliente.criarCliente()));

        var cliente =  controller.pegarClientePeloId(1L);

        assertThat(cliente)
                .isNotNull()
                .isInstanceOf(Cliente.class);

        verify(service,times(1)).pegarID(anyLong());


    }

    @Test
    void testeSeSelocionarClientePeloIDVazio(){
        when(service.pegarID(anyLong()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> controller.pegarClientePeloId(12L))
                .hasMessage("Cliente não encontrado")
                .isInstanceOf(ResultadoVazioErro.class);

        verify(service,times(1)).pegarID(anyLong());

    }
}

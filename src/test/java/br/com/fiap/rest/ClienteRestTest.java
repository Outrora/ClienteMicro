package br.com.fiap.rest;

import br.com.fiap.controller.ClienteController;
import br.com.fiap.domain.services.ClienteService;
import br.com.fiap.helps.CriarCliente;
import br.com.fiap.helps.ValidarCliente;
import br.com.fiap.rest.base.MesagemResposta;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


public class ClienteRestTest {

    @Mock
    ClienteController controller;

    ClienteRest rest;


    AutoCloseable openMocks;

    @BeforeEach
    void init() {
        openMocks = MockitoAnnotations.openMocks(this);
        rest = new ClienteRest(controller);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void pegarPeloCpfCorretamente(){
        var cliente1 = CriarCliente.criarCliente();
        when(controller.pegarClientePeloCPF(anyString()))
                .thenReturn(cliente1);

        var cliente = rest.pegarPeloCpf(CriarCliente.gerarCPF());

        ValidarCliente.verificarCliente(cliente1,cliente);

        verify(controller,times(1)).pegarClientePeloCPF(anyString());
    }

    @Test
    void pegarPeloIDCorretamente(){
        var cliente1 = CriarCliente.criarCliente();
        when(controller.pegarClientePeloId(anyLong()))
                .thenReturn(cliente1);

        var cliente = rest.pegarPeloID(CriarCliente.gerarId().get());

        ValidarCliente.verificarCliente(cliente1,cliente);

        verify(controller,times(1)).pegarClientePeloId(anyLong());
    }

    @Test
    void vericarSeEstarChamndoCadastarCorretamente(){
        var cliente = new ClienteRequest(
                CriarCliente.gerarNome(),
                CriarCliente.gerarCPF(),
                CriarCliente.gerarEmail()
        );

        doNothing().when(controller).salvarCliente(cliente);
         var response = rest.inserirCliente(cliente);

         assertThat(response)
                 .isInstanceOf(Response.class)
                         .isNotNull();

         assertThat(response.getEntity())
                 .isEqualTo(new MesagemResposta("Criado com sucesso"));

         assertThat(response.getStatus())
                 .isEqualTo(Response.Status.CREATED.getStatusCode());

         verify(controller,times(1))
                 .salvarCliente(any(ClienteRequest.class));

    }
}

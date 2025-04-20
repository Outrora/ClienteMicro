package br.com.fiap.services;

import br.com.fiap.database.dto.ClienteDTO;
import br.com.fiap.database.repository.ClienteRepository;
import br.com.fiap.domain.entities.Cliente;
import br.com.fiap.domain.services.ClienteService;
import br.com.fiap.domain.services.validacoes.ValidacaoCliente;
import br.com.fiap.domain.services.validacoes.ValidarClienteBanco;
import br.com.fiap.exception.ErroValidacao;
import br.com.fiap.helps.CriarCliente;
import br.com.fiap.helps.ValidarCliente;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


class ClienteServiceTest {

    @Mock
    private ClienteRepository repository;

    private List<ValidacaoCliente> validadores;
    private List<ValidarClienteBanco> validadotesBanco;

    private ClienteService service;

    AutoCloseable openMocks;

    @BeforeEach
    void init() {
        openMocks = MockitoAnnotations.openMocks(this);
        validadores = new ArrayList<>();
        validadotesBanco = new ArrayList<>();
        service = new ClienteService(repository,validadores,validadotesBanco);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }


    @Test
    void verificarSeEstarRetornandoClienteCorretamentePeloId(){
        var cliente = CriarCliente.criarCliente();
        var clienteExite = CriarCliente.criarClienteDTO(cliente,1L);

        when(repository.buscarID(anyLong())).thenReturn(clienteExite);

        var clienteRetorno = service.pegarID(1L);

        ValidarCliente.verificarCliente(cliente,clienteRetorno);

        verify(repository, times(1)).buscarID(anyLong());

    }

    @Test
    void verificarSeEstarRetornandoClienteVazioCorretamentePeloId(){

        when(repository.buscarID(anyLong())).thenReturn(Optional.empty());

        var clienteRetorno = service.pegarID(1L);

        assertThat(clienteRetorno)
                .isEmpty();

        verify(repository, times(1)).buscarID(anyLong());

    }

    @Test
    void verificarSeEstarRetornandoClienteCorretamentePeloCPF(){
        var cliente = CriarCliente.criarCliente();
        var clienteExite = CriarCliente.criarClienteDTO(cliente,1L);

        when(repository.buscarCPF(anyString())).thenReturn(clienteExite);

        var clienteRetorno = service.pegarCPF("1234567");

        ValidarCliente.verificarCliente(cliente,clienteRetorno);

        verify(repository, times(1)).buscarCPF(anyString());

    }

    @Test
    void verificarSeEstarRetornandoClienteVazioCorretamentePeloCPF(){

        when(repository.buscarCPF(anyString())).thenReturn(Optional.empty());

        var clienteRetorno = service.pegarCPF("1234567");

        assertThat(clienteRetorno)
                .isEmpty();

        verify(repository, times(1)).buscarCPF(anyString());

    }

    @Test
    void verificarSeEstarCastrandoCorretamente(){

        when(repository.buscarEmailOuCPF(anyString(),anyString())).thenReturn(Optional.empty());
        doNothing().when(repository).cadastrarCliente(any(Cliente.class));

        service.casdastrarCliente(CriarCliente.criarClienteSemId());

        verify(repository, times(1)).buscarEmailOuCPF(anyString(),anyString());
        verify(repository, times(1)).cadastrarCliente(any(Cliente.class));

    }

    @Test
    void verificarSeEstarCastrandoCorretamentePassndoPeloValidadores(){

        validadores.add(new ValidadorSimples());
        validadotesBanco.add(new ValidadorComplexa());


        when(repository.buscarEmailOuCPF(anyString(),anyString())).thenReturn(Optional.empty());
        doNothing().when(repository).cadastrarCliente(any(Cliente.class));

        service.casdastrarCliente(CriarCliente.criarClienteSemId());

        verify(repository, times(1)).buscarEmailOuCPF(anyString(),anyString());
        verify(repository, times(1)).cadastrarCliente(any(Cliente.class));

    }

    private class ValidadorSimples implements ValidacaoCliente {

        @Override
        public void validar(Cliente cliente) throws ErroValidacao {

        }
    }

    private class ValidadorComplexa implements ValidarClienteBanco {

        @Override
        public void validar(Cliente cliente, Optional<ClienteDTO> clienteDTO) throws ErroValidacao {

        }
    }

}

package br.com.fiap.database;


import br.com.fiap.database.dto.ClienteDTO;
import br.com.fiap.database.repository.ClienteRepository;
import br.com.fiap.helps.CriarCliente;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class clienteRepositoryTest {

    @Spy
    ClienteRepository repository;

    @Mock
    PanacheQuery<ClienteDTO> mockQuery;

    AutoCloseable openMocks;


    @BeforeEach
    void init() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void estarFuncionadoCorretamenteOCatrarCliente(){
        doNothing().when(repository).persist(any(ClienteDTO.class));

        repository.cadastrarCliente(CriarCliente.criarCliente());

        verify(repository,times(1)).persist(any(ClienteDTO.class));

    }

    @Test
    void estarFuncionadoCorretamenteOBuscarCPF(){

        doReturn(mockQuery).when(repository).find(anyString(),anyString());
        doReturn(CriarCliente.criarClienteDTO()).when(mockQuery).firstResultOptional();

        var cliente = repository.buscarCPF("123456");


        assertThat(cliente)
                .isPresent()
                .isNotNull();

        verify(repository,times(1)).find(anyString(),anyString());
        verify(mockQuery,times(1)).firstResultOptional();

    }

    @Test
    void estarFuncionadoCorretamenteOBuscarID(){

        doReturn(CriarCliente.criarClienteDTO())
                .when(repository)
                .findByIdOptional(anyLong());


        var cliente = repository.buscarID(123456L);


        assertThat(cliente)
                .isPresent()
                .isNotNull();

        verify(repository,times(1)).findByIdOptional(anyLong());

    }

    @Test
    void estarFuncionadoCorretamenteBuscarEmailOuCPF(){

        doReturn(mockQuery).when(repository).find(anyString(),anyString(),anyString());
        doReturn(CriarCliente.criarClienteDTO()).when(mockQuery).firstResultOptional();

        var cliente = repository.buscarEmailOuCPF(
                CriarCliente.gerarEmail(),
                CriarCliente.gerarCPF());


        assertThat(cliente)
                .isPresent()
                .isNotNull();

        verify(repository,times(1)).find(anyString(),anyString(),anyString());
        verify(mockQuery,times(1)).firstResultOptional();

    }







}

package br.com.fiap.mapper;


import br.com.fiap.database.dto.ClienteDTO;
import br.com.fiap.domain.entities.Cliente;
import br.com.fiap.helps.CriarCliente;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class ClienteMapperTest {

    @Test
    void deveConverterClienteParaDTO() {

        var clienteDTO = ClienteMapper.ClienteParaDTO(CriarCliente.criarClienteSemId());
        assertThat(clienteDTO)
                .isNotNull()
                .isInstanceOf(ClienteDTO.class);
    }

}

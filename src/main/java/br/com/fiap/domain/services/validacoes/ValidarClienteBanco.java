package br.com.fiap.domain.services.validacoes;

import br.com.fiap.database.dto.ClienteDTO;
import br.com.fiap.domain.entities.Cliente;
import br.com.fiap.exception.ErroValidacao;

import java.util.Optional;

public interface ValidarClienteBanco {


        void validar(Cliente cliente, Optional<ClienteDTO> clienteDTO) throws ErroValidacao;


}

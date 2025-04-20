package br.com.fiap.domain.services.validacoes;

import br.com.fiap.database.dto.ClienteDTO;
import br.com.fiap.domain.entities.Cliente;
import br.com.fiap.exception.ErroValidacao;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class VerificarEmailExistente implements ValidarClienteBanco{
    @Override
    public void validar(Cliente cliente, Optional<ClienteDTO> clienteDTO) throws ErroValidacao {
       if (clienteDTO.isEmpty()) return;

       if (cliente.getEmail().equals(clienteDTO.get().getEmail())) {
            throw new ErroValidacao("Email já cadastrado");
       }

    }
}

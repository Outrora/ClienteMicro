package br.com.fiap.mapper;


import br.com.fiap.domain.entities.Cliente;
import br.com.fiap.database.dto.ClienteDTO;
import br.com.fiap.rest.ClienteRequest;

import java.sql.Timestamp;
import java.util.Optional;

public abstract class ClienteMapper {

    public static ClienteDTO ClienteParaDTO (Cliente cliente){


        var clienteDTO = ClienteDTO
                .builder()
                .cpf(cliente.getCpf())
                .email(cliente.getEmail())
                .inclucao(new Timestamp(System.currentTimeMillis()))
                .nome(cliente.getNome())
                .build();

        if (cliente.getId().isPresent()) {
            clienteDTO.setId(clienteDTO.getId());
        }

        return clienteDTO;

    }

    public static Cliente toCliente(ClienteDTO clienteDTO){

        return Cliente
                .builder()
                .cpf(clienteDTO.getCpf())
                .email(clienteDTO.getEmail())
                .nome(clienteDTO.getNome())
                .id(Optional.of(clienteDTO.getId()))
                .build();
    }

    public  static Cliente  toCliente(ClienteRequest clienteRequest){
        return Cliente
                .builder()
                .cpf(clienteRequest.CPF())
                .email(clienteRequest.email())
                .nome(clienteRequest.nome())
                .id(Optional.empty())
                .build();
    }
}

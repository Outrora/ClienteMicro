package br.com.fiap.controller;

import br.com.fiap.domain.entities.Cliente;
import br.com.fiap.domain.services.ClienteService;
import br.com.fiap.exception.ResultadoVazioErro;
import br.com.fiap.mapper.ClienteMapper;
import br.com.fiap.rest.ClienteRequest;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class ClienteController {

    ClienteService clienteService;
    private final String NOT_FOUND = "Cliente não encontrado";

    @Inject
    public ClienteController (ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    public void salvarCliente (ClienteRequest cliente){
        clienteService.casdastrarCliente(ClienteMapper.toCliente(cliente));
    }

    public Cliente pegarClientePeloCPF(String cpf) {
        return  clienteService
                .pegarCPF(cpf)
                .orElseThrow(() -> new ResultadoVazioErro(NOT_FOUND));
    }

    public Cliente pegarClientePeloId(Long id) {
        return clienteService.pegarID(id)
                .orElseThrow(() -> new ResultadoVazioErro(NOT_FOUND));
    }
}

package br.com.fiap.domain.services;

import br.com.fiap.database.repository.ClienteRepository;
import br.com.fiap.domain.entities.Cliente;
import br.com.fiap.domain.services.validacoes.ValidacaoCliente;
import br.com.fiap.domain.services.validacoes.ValidarClienteBanco;
import br.com.fiap.mapper.ClienteMapper;
import io.quarkus.arc.All;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ClienteService {

    ClienteRepository repository;
    List<ValidacaoCliente> validadores;
    List<ValidarClienteBanco> validadotesBanco;


    @Inject
    public ClienteService(ClienteRepository repository,
                          @All List<ValidacaoCliente> validadores,
                          @All List<ValidarClienteBanco> validadotesBanco){
        this.repository = repository;
        this.validadores = validadores;
        this.validadotesBanco = validadotesBanco;
    }


    public Optional<Cliente> pegarID(Long id) {
        return repository
                .buscarID(id)
                .map(ClienteMapper::toCliente);
    }

    public Optional<Cliente> pegarCPF(String cpf) {
        cpf = cpf.trim().replaceAll("\\D", "");
        return repository
                .buscarCPF(cpf)
                .map(ClienteMapper::toCliente);

    }

    public void casdastrarCliente(Cliente cliente){
        var clienteVerificado = repository.buscarEmailOuCPF(cliente.getEmail(), cliente.getCpf());

        for (var validador: validadores) {
            validador.validar(cliente);
        }

        for (var validador: validadotesBanco) {
            validador.validar(cliente,clienteVerificado);
        }

        repository.cadastrarCliente(cliente);

    }
}

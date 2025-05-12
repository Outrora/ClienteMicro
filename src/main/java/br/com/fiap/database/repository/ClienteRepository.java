package br.com.fiap.database.repository;

import br.com.fiap.domain.entities.Cliente;
import br.com.fiap.database.dto.ClienteDTO;
import br.com.fiap.mapper.ClienteMapper;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<ClienteDTO> {

    @Transactional
    public void cadastrarCliente(Cliente cliente) {
        persist(ClienteMapper.ClienteParaDTO(cliente));
    }

    public Optional<ClienteDTO> buscarCPF(String CPF) {
        return find("cpf", CPF).firstResultOptional();
    }


    public Optional<ClienteDTO> buscarID(Long id) {
        return findByIdOptional(id);
    }

    public Optional<ClienteDTO> buscarEmailOuCPF(String email, String cpf){
        return find("email = ?1 OR cpf = ?2",email,cpf)
                .firstResultOptional();
    }
}

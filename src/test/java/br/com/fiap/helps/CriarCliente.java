package br.com.fiap.helps;

import br.com.fiap.database.dto.ClienteDTO;
import br.com.fiap.domain.entities.Cliente;
import br.com.fiap.mapper.ClienteMapper;

import java.util.Optional;
import java.util.Random;

public abstract class CriarCliente {

    private static Random random = new Random();

    // Método para gerar CPF aleatório (apenas números)
    public static String gerarCPF() {
        StringBuilder cpf = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            cpf.append(random.nextInt(10)); // Gera números aleatórios de 0 a 9
        }
        return cpf.toString();
    }

    public static Optional<Long> gerarId() {
        var id = random.nextInt(10);
        return Optional.of((long) id);
    }


    // Método para gerar Email aleatório
    public static String gerarEmail() {
        String domain = "@gmail.com";
        StringBuilder email = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            email.append((char) (random.nextInt(26) + 97)); // Gera uma letra aleatória de a-z
        }
        email.append(domain);
        return email.toString();
    }

    // Método para gerar
    public static String gerarNome() {
        String domain = " Ferreira";
        StringBuilder email = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            email.append((char) (random.nextInt(26) + 97)); // Gera uma letra aleatória de a-z
        }
        email.append(domain);
        return email.toString();
    }

    // Método para criar Cliente com CPF e Email aleatório
    public static Cliente criarCliente() {
        return Cliente
                .builder()
                .id(gerarId())
                .cpf(gerarCPF())
                .nome(gerarNome())
                .email(gerarEmail())
                .build();
    }

    public static Cliente criarClienteSemId() {
        return Cliente
                .builder()
                .id(Optional.empty())
                .cpf(gerarCPF())
                .nome(gerarNome())
                .email(gerarEmail())
                .build();
    }

    public static Optional<ClienteDTO> criarClienteDTO(Cliente cliente){

        return Optional.of(ClienteMapper.ClienteParaDTO(cliente));
    }

    public static Optional<ClienteDTO> criarClienteDTO(Cliente cliente,Long id){
        var dto = ClienteMapper.ClienteParaDTO(cliente);
        dto.setId(id);
        return Optional.of(dto);
    }

    public static Optional<ClienteDTO> criarClienteDTO(){
        return Optional.of(ClienteMapper.ClienteParaDTO(CriarCliente.criarCliente()));
    }

    public static Optional<ClienteDTO> criarClienteDTO(Long id){
        var dto = ClienteMapper.ClienteParaDTO(CriarCliente.criarCliente());
        dto.setId(id);
        return Optional.of(dto);
    }
}

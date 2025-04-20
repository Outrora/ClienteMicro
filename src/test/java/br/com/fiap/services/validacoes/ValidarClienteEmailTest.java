package br.com.fiap.services.validacoes;

import br.com.fiap.domain.services.validacoes.ValidarClienteBanco;
import br.com.fiap.domain.services.validacoes.VerificarCPFExistente;
import br.com.fiap.domain.services.validacoes.VerificarEmailExistente;
import br.com.fiap.exception.ErroValidacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static br.com.fiap.helps.CriarCliente.criarCliente;
import static br.com.fiap.helps.CriarCliente.criarClienteDTO;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ValidarClienteEmailTest {

    ValidarClienteBanco verificarEmail;
    ValidarClienteBanco verificarCPF;

    @BeforeEach
    void init(){
        verificarEmail = new VerificarEmailExistente();
        verificarCPF = new VerificarCPFExistente();
    }


    @Test
    void testarSeEmailExite(){
        var cliente = criarCliente();
        assertThatThrownBy(() -> verificarEmail.validar(cliente, criarClienteDTO(cliente)))
                .isInstanceOf(ErroValidacao.class)
                .hasMessage("Email já cadastrado");
    }

    @Test
    void testarQuandoClienteDTOeVazio(){
        var cliente = criarCliente();

        assertThatCode(() -> verificarEmail.validar(cliente,Optional.empty()))
                .doesNotThrowAnyException();
    }

    @Test
    void testarQuandoClienteDiferente(){
        var cliente = criarCliente();
        var cliente2 = criarCliente();

        assertThatCode(() -> verificarEmail.validar(cliente,criarClienteDTO(cliente2)))
                .doesNotThrowAnyException();
    }


}

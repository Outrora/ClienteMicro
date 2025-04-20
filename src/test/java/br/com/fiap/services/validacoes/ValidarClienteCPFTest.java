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

class ValidarClienteCPFTest {


    ValidarClienteBanco verificarCPF;

    @BeforeEach
    void init(){
        verificarCPF = new VerificarCPFExistente();
    }


    @Test
    void testarSeCPFExite(){
        var cliente = criarCliente();
        assertThatThrownBy(() -> verificarCPF.validar(cliente, criarClienteDTO(cliente)))
                .isInstanceOf(ErroValidacao.class)
                .hasMessage("CPF já cadastrado");
    }

    @Test
    void testarQuandoClienteDTOeVazio(){
        var cliente = criarCliente();

        assertThatCode(() -> verificarCPF.validar(cliente,Optional.empty()))
                .doesNotThrowAnyException();
    }

    @Test
    void testarQuandoClienteDiferente(){
        var cliente = criarCliente();
        var cliente2 = criarCliente();

        assertThatCode(() -> verificarCPF.validar(cliente,criarClienteDTO(cliente2)))
                .doesNotThrowAnyException();
    }


}

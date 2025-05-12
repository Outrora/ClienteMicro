package br.com.fiap.services.validacoes;

import br.com.fiap.domain.entities.Cliente;
import br.com.fiap.domain.services.validacoes.ValidadeCPF;
import static org.assertj.core.api.Assertions.*;

import br.com.fiap.exception.ErroValidacao;
import org.junit.jupiter.api.Test;

public class ValidarCPFTest {

    @Test
    public void testeValidarCPFValido() {

        var cliente = this.criarCliente("12345678909");
        var cliente2 = this.criarCliente("11144477735");
        ValidadeCPF validadeCPF = new ValidadeCPF();

        assertThatCode(() -> validadeCPF.validar(cliente))
                .doesNotThrowAnyException();

        assertThatCode(() -> validadeCPF.validar(cliente2))
                .doesNotThrowAnyException();

    }

    @Test
    public void testValidarCpfInvalido() {
        Cliente cliente = this.criarCliente( "12345678900");
        this.acionarVerificarValidacao(cliente);
    }

    @Test
    public void testValidarCpfComSequenciaInvalida() {
        Cliente cliente = this.criarCliente( "11111111111");
        this.acionarVerificarValidacao(cliente);
    }

    @Test
    public void testValidarCpfComSequenciaInvalida2() {
        Cliente cliente = this.criarCliente( "77777777777");
        this.acionarVerificarValidacao(cliente);
    }

    @Test
    public void testValidarCpfComTamanhoInvalido() {
        Cliente cliente = this.criarCliente(  "111");
        this.acionarVerificarValidacao(cliente);
    }

    @Test
    public void testValidarCpfInvalido3() {
        Cliente cliente = this.criarCliente(  "12.34.56-78");
        this.acionarVerificarValidacao(cliente);
    }



    @Test
    public void testValidarCpfComLetras() {
        Cliente cliente = this.criarCliente(  "aaaaaabaaaa");
        this.acionarVerificarValidacao(cliente);
    }


    private void acionarVerificarValidacao(Cliente cliente) {
        ValidadeCPF validadeCPF = new ValidadeCPF();
        assertThatThrownBy(() -> validadeCPF.validar(cliente))
                .isInstanceOf(ErroValidacao.class)
                .hasMessage("CPF Invalido");
    }

    private Cliente criarCliente(String cpf){
        return Cliente.builder()
                .cpf(cpf)
                .build();
    }
}

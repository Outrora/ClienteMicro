package br.com.fiap.domain.services.validacoes;

import br.com.fiap.domain.entities.Cliente;
import br.com.fiap.exception.ErroValidacao;


public interface ValidacaoCliente {

    void validar(Cliente cliente) throws ErroValidacao;

}

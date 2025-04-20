package br.com.fiap.domain.services.validacoes;

import br.com.fiap.domain.entities.Cliente;
import br.com.fiap.exception.ErroValidacao;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.InputMismatchException;

@ApplicationScoped
public class ValidadeCPF implements ValidacaoCliente {

    @Override
    public void validar(Cliente cliente) throws ErroValidacao {

        if (!isCPF(cliente.getCpf())) {
            System.out.println(cliente.getCpf());
            throw new ErroValidacao("CPF Invalido");
        }
    }

    private boolean isCPF(String CPF) {
        // Remove caracteres não numéricos (pontos, hífens, etc)
        CPF = CPF.replaceAll("[^\\d]", "");

        // Considera erro CPFs com sequência de números iguais (como 000.000.000-00)
        if (CPF.length() != 11 || CPF.matches("(\\d)\\1{10}"))
            return false;

        char dig10, dig11;
        int sm, num, r, peso;


            // Cálculo do 1º dígito verificador
            sm = 0;
            peso = 10;
            for (int i = 0; i < 9; i++) {
                num = Integer.parseInt(String.valueOf(CPF.charAt(i)));
                sm += num * peso--;
            }

            r = 11 - (sm % 11);
            dig10 = (r == 10 || r == 11) ? '0' : (char) (r + 48);

            // Cálculo do 2º dígito verificador
            sm = 0;
            peso = 11;
            for (int i = 0; i < 10; i++) {
                num = Integer.parseInt(String.valueOf(CPF.charAt(i)));
                sm += num * peso--;
            }

            r = 11 - (sm % 11);
            dig11 = (r == 10 || r == 11) ? '0' : (char) (r + 48);

            // Verifica se os dígitos calculados conferem com os dígitos informados
            return (dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10));


    }

}

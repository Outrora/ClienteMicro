package br.com.fiap.step;


import br.com.fiap.rest.ClienteRequest;
import io.quarkiverse.cucumber.ScenarioScope;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ScenarioScope
@Getter
@Setter
@NoArgsConstructor
public class Bean {

    private String cpf;
    private String nome;
    private String email;

    public ClienteRequest retonarCliente(){
        return new ClienteRequest(nome, cpf, email);
    }
}

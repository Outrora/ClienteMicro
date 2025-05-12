package br.com.fiap.step;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import jakarta.inject.Inject;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.extension.MediaType;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

public class Passos {

    @Inject
    Bean bean;

    @Dado("que prenchi o cpf com {string}")
    public void quePrenchiOCpfComCpf(String cpf) {
        bean.setCpf(cpf);
    }

    @Dado("que prenchi o nome com {string}")
    public void quePrenchiONomeComNome(String nome) {
        bean.setNome(nome);
        
    }

    @Dado("que prenchi o email com {string}")
    public void quePrenchiOEmailComEmail(String email) {
        bean.setEmail(email);
        
    }

    @Quando("envio os dados do formulario")
    public void envioOsDadosDoFormulario() {
        var cliente = bean.retonarCliente();
        given()
                .contentType(MediaType.APPLICATION_JSON.toString())
                .body(cliente)
                .when()
                .post("/")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("$",hasKey("mensagem"))
                .body("mensagem",equalTo("Criado com sucesso"));
        
    }

    @Entao("o cliente deve retornar com sucesso quando busco o cpf {string}")
    public void oClienteDeveRetornarComSucessoQuandoBuscoOCpfCpf(String cpf) {
        given()
                .contentType(MediaType.APPLICATION_JSON.toString())
                .get("/cpf/{cpf}",cpf)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath("schemas/ConsultarCliente.json"));
    }
}

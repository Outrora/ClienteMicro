package br.com.fiap.it;

import br.com.fiap.helps.CriarCliente;
import br.com.fiap.rest.ClienteRequest;
import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.MediaType;


import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;


@QuarkusTest
class IntegracaoTest {



    @Test
    void deveTrazerClientePeloId(){
        given()
                .contentType(MediaType.APPLICATION_JSON.toString())
                .get("/id/{id}",1)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath("schemas/ConsultarCliente.json"));
    }

    @Test
    void deveTrazerClientePeloIdNotFound(){
        given()
                .contentType(MediaType.APPLICATION_JSON.toString())
                .get("/id/{id}",5)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("$",hasKey("error"))
                .body("error",equalTo("Cliente não encontrado"))
        ;
    }

    @Test
    void deveTrazerClientePeloCPF(){
        given()
                .contentType(MediaType.APPLICATION_JSON.toString())
                .get("/cpf/{cpf}","12345678700")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath("schemas/ConsultarCliente.json"));
    }

    @Test
    void deveTrazerClientePeloCPFNaoEncontrado(){
        given()
                .contentType(MediaType.APPLICATION_JSON.toString())
                .get("/cpf/{cpf}","12345678fsdf900")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("$",hasKey("error"))
                .body("error",equalTo("Cliente não encontrado"));
    }

    @Test
    void deveCadastrarClienteComSucesso(){

        var cliente = new ClienteRequest(CriarCliente.gerarNome(),
                "603.200.360-49",
                CriarCliente.gerarEmail());

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

    @Test
    void deveCadastrarClienteComErroCpfInvalido(){

        var cliente = new ClienteRequest(CriarCliente.gerarNome(),
                "603.200.360-48",
                CriarCliente.gerarEmail());

        given()
                .contentType(MediaType.APPLICATION_JSON.toString())
                .body(cliente)
                .when()
                .post("/")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("$",hasKey("error"))
                .body("error",equalTo("CPF Invalido"));
    }

    @Test
    void deveCadastrarClienteComErroCpfExitente(){

        var cliente = new ClienteRequest(CriarCliente.gerarNome(),
                "95136137045",
                CriarCliente.gerarEmail());

        given()
                .contentType(MediaType.APPLICATION_JSON.toString())
                .body(cliente)
                .when()
                .post("/")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("$",hasKey("error"))
                .body("error",equalTo("CPF já cadastrado"));
    }

    @Test
    void deveCadastrarClienteComErroEmailExitente(){

        var cliente = new ClienteRequest(CriarCliente.gerarNome(),
                "078.469.630-66",
                "joao.silva@email.com");

        given()
                .contentType(MediaType.APPLICATION_JSON.toString())
                .body(cliente)
                .when()
                .post("/")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("$",hasKey("error"))
                .body("error",equalTo("Email já cadastrado"));
    }




}

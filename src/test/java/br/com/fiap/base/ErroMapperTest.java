package br.com.fiap.base;

import br.com.fiap.exception.ErroValidacao;
import br.com.fiap.exception.ResultadoVazioErro;
import br.com.fiap.rest.base.ErrorMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.quarkus.test.junit.QuarkusTest;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class ErroMapperTest {

    @Inject
    ErrorMapper mapper;


    @Test
    void deveRetornar404ParaResultadoVazioErro() {
        ResultadoVazioErro ex = new ResultadoVazioErro("Nada encontrado");

        Response response = mapper.toResponse(ex);
        assertEquals(404, response.getStatus());

        ObjectNode json = (ObjectNode) response.getEntity();
        assertEquals(ResultadoVazioErro.class.getName(), json.get("exceptionType").asText());
        assertEquals(404, json.get("code").asInt());
        assertEquals("Nada encontrado", json.get("error").asText());
    }

    @Test
    void deveRetornar400ParaErroValidacao() {
        ErroValidacao ex = new ErroValidacao("Erro de validação");

        Response response = mapper.toResponse(ex);
        assertEquals(400, response.getStatus());

        ObjectNode json = (ObjectNode) response.getEntity();
        assertEquals(ErroValidacao.class.getName(), json.get("exceptionType").asText());
        assertEquals(400, json.get("code").asInt());
        assertEquals("Erro de validação", json.get("error").asText());
    }

    @Test
    void deveRetornar500ParaErroGenerico() {
        Exception ex = new Exception("Erro desconhecido");

        Response response = mapper.toResponse(ex);
        assertEquals(500, response.getStatus());

        ObjectNode json = (ObjectNode) response.getEntity();
        assertEquals(Exception.class.getName(), json.get("exceptionType").asText());
        assertEquals(500, json.get("code").asInt());
        assertEquals("Erro desconhecido", json.get("error").asText());
    }

    @Test
    void deveIgnorarMensagemNula() {
        Exception ex = new Exception();

        Response response = mapper.toResponse(ex);
        assertEquals(500, response.getStatus());

        ObjectNode json = (ObjectNode) response.getEntity();
        assertEquals(Exception.class.getName(), json.get("exceptionType").asText());
        assertEquals(500, json.get("code").asInt());
        assertNull(json.get("error")); // campo "error" não deve existir
    }
}

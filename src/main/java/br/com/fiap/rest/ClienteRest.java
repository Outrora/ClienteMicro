package br.com.fiap.rest;

import br.com.fiap.controller.ClienteController;
import br.com.fiap.domain.entities.Cliente;
import br.com.fiap.rest.base.MesagemResposta;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@RequestScoped
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Cliente", description = "Endpoints do clientes")
public class ClienteRest {

    ClienteController controller;

    public ClienteRest (ClienteController clienteController) {
        this.controller = clienteController;
    }

    @GET
    @Path("cpf/{cpf}")
    @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Cliente Retornado com Sucesso",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = Cliente.class)
                            )
                    ),
                    @APIResponse(
                            responseCode = "404",
                            description = "Cliente não Encontrado",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = MesagemResposta.class)
                            )
                    )

            }
    )
    public Cliente pegarPeloCpf(@PathParam("cpf") String cpf){
        return controller.pegarClientePeloCPF(cpf);
    }


    @GET
    @Path("id/{id}")
    @APIResponses(
            value = {
                    @APIResponse(
                        responseCode = "200",
                        description = "Cliente Retornado com Sucesso",
                        content = @Content(
                                mediaType = MediaType.APPLICATION_JSON,
                                schema = @Schema(implementation = Cliente.class)
                        )
                    ),
                    @APIResponse(
                        responseCode = "404",
                        description = "Cliente não Encontrado",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = MesagemResposta.class)
                            )
                    )

            }
    )
    public Cliente pegarPeloID(@PathParam("id") Long id){
        return controller.pegarClientePeloId(id);
    }


    @POST
    @APIResponses(
    value = {
            @APIResponse(
                responseCode = "201",
                description = "Cliente criado com sucesso"
            ),
            @APIResponse(responseCode = "400", description = "Dados inválidos"),
    }
)
    public Response inserirCliente(ClienteRequest request){
        controller.salvarCliente(request);

        return Response
                .status(Response.Status.CREATED)
                .entity(new MesagemResposta("Criado com sucesso"))
                .build();
    }
}

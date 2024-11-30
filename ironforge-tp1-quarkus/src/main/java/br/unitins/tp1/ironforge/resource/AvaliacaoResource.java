package br.unitins.tp1.ironforge.resource;

import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.unitins.tp1.ironforge.dto.avaliacao.AvaliacaoRequestDTO;
import br.unitins.tp1.ironforge.dto.avaliacao.AvaliacaoResponseDTO;
import br.unitins.tp1.ironforge.model.avaliacao.Avaliacao;
import br.unitins.tp1.ironforge.service.avaliacao.AvaliacaoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/avaliacoes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AvaliacaoResource {

    @Inject
    public AvaliacaoService avaliacaoService;

    @Inject
    public JsonWebToken jsonWebToken;

    @GET
    @Path("/{id}")
    @RolesAllowed({ "Adm" })
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(AvaliacaoResponseDTO.valueOf(avaliacaoService.findById(id))).build();
    }

    @RolesAllowed({ "User" })
    @GET
    public Response findAll() {
        String username = jsonWebToken.getSubject();
        List<Avaliacao> avaliacaos = avaliacaoService.findAll(username);
        return Response.ok(avaliacaos.stream().map(AvaliacaoResponseDTO::valueOf).toList()).build();
    }

    @POST
    @RolesAllowed({ "User" })
    public Response create(@Valid AvaliacaoRequestDTO avaliacao) {
        String username = jsonWebToken.getSubject();
        return Response.status(Status.CREATED)
                .entity(AvaliacaoResponseDTO.valueOf(avaliacaoService.create(avaliacao, username)))
                .build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({ "User" })
    public Response update(@PathParam("id") Long id, @Valid AvaliacaoRequestDTO avaliacao) {
        String username = jsonWebToken.getSubject();
        avaliacaoService.update(id, avaliacao, username);
        return Response.noContent().build();
    }

    @DELETE
    @RolesAllowed({ "User" })
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        String username = jsonWebToken.getSubject();
        avaliacaoService.delete(id, username);
        return Response.noContent().build();
    }

}

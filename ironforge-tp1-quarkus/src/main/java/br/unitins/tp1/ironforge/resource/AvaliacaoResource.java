package br.unitins.tp1.ironforge.resource;

import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

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

    private static final Logger LOG = Logger.getLogger(AvaliacaoResource.class);

    @Inject
    public AvaliacaoService avaliacaoService;

    @Inject
    public JsonWebToken jsonWebToken;

    @GET
    @Path("/{id}")
    @RolesAllowed({ "Cliente", "Administrador" })
    public Response findById(@PathParam("id") Long id) {
        LOG.infof("Execução do método findById. ID da avaliação: %d", id);
        return Response.ok(AvaliacaoResponseDTO.valueOf(avaliacaoService.findById(id))).build();
    }

    @GET
    @RolesAllowed({ "Cliente", "Administrador" })
    public Response findAll() {
        String username = jsonWebToken.getSubject();
        LOG.infof("Execução do método findAll. Usuário: %s", username);
        List<Avaliacao> avaliacaos = avaliacaoService.findAll(username);
        return Response.ok(avaliacaos.stream().map(AvaliacaoResponseDTO::valueOf).toList()).build();
    }

    @POST
    @RolesAllowed({ "Cliente", "Administrador" })
    public Response create(@Valid AvaliacaoRequestDTO avaliacao) {
        String username = jsonWebToken.getSubject();
        LOG.infof("Execução do método create. Usuário: %s. Dados da avaliação: %s", username, avaliacao);
        return Response.status(Status.CREATED)
                .entity(AvaliacaoResponseDTO.valueOf(avaliacaoService.create(avaliacao, username)))
                .build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({ "Cliente", "Administrador" })
    public Response update(@PathParam("id") Long id, @Valid AvaliacaoRequestDTO avaliacao) {
        String username = jsonWebToken.getSubject();
        LOG.infof("Execução do método update. Usuário: %s. Atualizando avaliação com ID: %d. Dados: %s", username, id,
                avaliacao);
        avaliacaoService.update(id, avaliacao, username);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({ "Cliente", "Administrador" })
    public Response delete(@PathParam("id") Long id) {
        String username = jsonWebToken.getSubject();
        LOG.infof("Execução do método delete. Usuário: %s. Deletando avaliação com ID: %d", username, id);
        avaliacaoService.delete(id, username);
        return Response.noContent().build();
    }
}
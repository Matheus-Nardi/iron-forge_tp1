package br.unitins.tp1.ironforge.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.tp1.ironforge.dto.estado.EstadoRequestDTO;
import br.unitins.tp1.ironforge.dto.estado.EstadoResponseDTO;
import br.unitins.tp1.ironforge.model.Estado;
import br.unitins.tp1.ironforge.service.estado.EstadoService;
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

@Path("/estados")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EstadoResource {

    private static final Logger LOG = Logger.getLogger(EstadoResource.class);

    @Inject
    public EstadoService estadoService;

    @GET
    @Path("/{id}")
    @RolesAllowed({ "Adm", "User" })
    public Response findById(@PathParam("id") Long id) {
        LOG.infof("Execucao do metodo findById. Id: %d", id);
        LOG.debug("DEBUG EXEMPLO");
        return Response.ok(EstadoResponseDTO.valueOf(estadoService.findById(id))).build();
    }

    @GET
    @Path("/search/{nome}")
    @RolesAllowed("Adm")
    public Response findByNome(@PathParam("nome") String nome) {
        List<Estado> estados = estadoService.findByNome(nome);
        return Response.ok(estados.stream().map(EstadoResponseDTO::valueOf).toList()).build();
    }

    @GET
    @Path("/search/{sigla}")
    public Response findBySigla(@PathParam("sigla") String sigla) {
        return Response.ok(estadoService.findBySigla(sigla)).build();
    }

    @GET
    public Response findAll() {
        List<Estado> estados = estadoService.findAll();
        return Response.ok(estados.stream().map(EstadoResponseDTO::valueOf).toList()).build();
    }

    @POST
    public Response create(@Valid EstadoRequestDTO estado) {
        return Response.status(Status.CREATED).entity(EstadoResponseDTO.valueOf(estadoService.create(estado))).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid EstadoRequestDTO estado) {
        estadoService.update(id, estado);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        estadoService.delete(id);
        return Response.noContent().build();
    }

}

package br.unitins.tp1.ironforge.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.tp1.ironforge.dto.lote.LoteRequestDTO;
import br.unitins.tp1.ironforge.dto.lote.LoteResponseDTO;
import br.unitins.tp1.ironforge.model.pedido.Lote;
import br.unitins.tp1.ironforge.service.lote.LoteService;
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
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/lotes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoteResource {

    private static final Logger LOG = Logger.getLogger(LoteResource.class);

    @Inject
    public LoteService loteService;

    @GET
    @RolesAllowed({ "Administrador", "Funcionario" })
    public Response getAll() {
        LOG.infof("Buscando todos os lotes.");
        List<Lote> lotes = loteService.findAll();
        return Response.ok(lotes.stream().map(LoteResponseDTO::valueOf)).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "Administrador", "Funcionario" })
    public Response findById(@PathParam("id") Long id) {
        LOG.infof("Buscando lote com ID: %d.", id);
        return Response.ok(LoteResponseDTO.valueOf(loteService.findById(id))).build();
    }

    @GET
    @Path("/search/codigo")
    @RolesAllowed({ "Administrador", "Funcionario" })
    public Response findByCodigo(@QueryParam("codigo") String codigo) {
        LOG.infof("Buscando lote com código: %s.", codigo);
        return Response.ok(LoteResponseDTO.valueOf(loteService.findByCodigo(codigo))).build();
    }

    @GET
    @Path("/search/whey")
    @RolesAllowed({ "Administrador", "Funcionario" })
    public Response findByWhey(@QueryParam("idWhey") Long idWhey) {
        LOG.infof("Buscando lotes para o whey com ID: %d.", idWhey);
        return Response.ok(LoteResponseDTO.valueOf(loteService.findByWhey(idWhey))).build();
    }

    @POST
    @RolesAllowed({ "Administrador", "Funcionario" })
    public Response create(@Valid LoteRequestDTO loteDTO) {
        LOG.infof("Criando novo lote com os dados: %s.", loteDTO);
        Lote lote = loteService.create(loteDTO);
        return Response.status(Status.CREATED).entity(LoteResponseDTO.valueOf(lote)).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({ "Administrador", "Funcionario" })
    public Response update(@PathParam("id") Long id, @Valid LoteRequestDTO loteToUpdate) {
        LOG.infof("Atualizando lote com ID: %d. Novos dados: %s.", id, loteToUpdate);
        loteService.update(id, loteToUpdate);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({ "Administrador", "Funcionario" })
    public Response delete(@PathParam("id") Long id) {
        LOG.infof("Deletando lote com ID: %d.", id);
        loteService.delete(id);
        return Response.noContent().build();
    }
}

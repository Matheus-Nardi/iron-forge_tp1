package br.unitins.tp1.ironforge.resource;

import java.util.List;

import br.unitins.tp1.ironforge.dto.lote.LoteRequestDTO;
import br.unitins.tp1.ironforge.dto.lote.LoteResponseDTO;
import br.unitins.tp1.ironforge.model.Lote;
import br.unitins.tp1.ironforge.service.lote.LoteService;
import jakarta.inject.Inject;
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

    @Inject
    public LoteService loteService;

    @GET
    public Response getAll() {
        List<Lote> lotes = loteService.findAll();
        return Response.ok(lotes.stream().map(LoteResponseDTO::valueOf)).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(LoteResponseDTO.valueOf(loteService.findById(id))).build();
    }

    @GET
    @Path("/search/codigo")
    public Response findByCodigo(@QueryParam("codigo") String codigo) {
        List<Lote> lotes = loteService.findByCodigo(codigo);
        return Response.ok(lotes.stream().map(LoteResponseDTO::valueOf).toList()).build();
    }

    @GET
    @Path("/search/whey")
    public Response findByWhey(@QueryParam("idWhey") Long idWhey) {
        List<Lote> lotes = loteService.findByWhey(idWhey);
        return Response.ok(lotes.stream().map(LoteResponseDTO::valueOf).toList()).build();
    }

    @POST
    public Response create(LoteRequestDTO loteDTO) {
        Lote lote = loteService.create(loteDTO);
        return Response.status(Status.CREATED).entity(LoteResponseDTO.valueOf(lote)).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, LoteRequestDTO loteToUpdate) {
        loteService.update(id, loteToUpdate);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        loteService.delete(id);
        return Response.noContent().build();
    }
}

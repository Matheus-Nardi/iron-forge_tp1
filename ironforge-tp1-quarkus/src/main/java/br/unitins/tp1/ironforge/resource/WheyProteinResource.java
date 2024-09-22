package br.unitins.tp1.ironforge.resource;

import java.util.List;

import br.unitins.tp1.ironforge.dto.whey.WheyProteinDTO;
import br.unitins.tp1.ironforge.dto.whey.WheyProteinResponseDTO;
import br.unitins.tp1.ironforge.service.whey.WheyProteinService;
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

@Path("/wheys")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WheyProteinResource {

    @Inject
    public WheyProteinService wheyService;

    @GET
    public Response getAll() {
        List<WheyProteinResponseDTO> wheys = wheyService.findAll();
        return Response.ok(wheys).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        WheyProteinResponseDTO whey = wheyService.findById(id);
        return Response.ok(whey).build();
    }

    @GET
    @Path("/search/nome")
    public Response findByNome(@QueryParam("nome") String nome) {
        List<WheyProteinResponseDTO> wheys = wheyService.findByNome(nome);
        return Response.ok(wheys).build();
    }

    @GET
    @Path("/search/preco")
    public Response findByPreco(@QueryParam("preco") Double preco) {
        List<WheyProteinResponseDTO> wheys = wheyService.findByPreco(preco);
        return Response.ok(wheys).build();
    }

    @POST
    public Response create(WheyProteinDTO wheyProteinDTO) {
        WheyProteinResponseDTO whey = wheyService.create(wheyProteinDTO);
        return Response.status(Status.CREATED).entity(whey).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, WheyProteinDTO wheyToUpdate) {
        wheyService.update(id, wheyToUpdate);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        wheyService.delete(id);
        return Response.noContent().build();
    }
}

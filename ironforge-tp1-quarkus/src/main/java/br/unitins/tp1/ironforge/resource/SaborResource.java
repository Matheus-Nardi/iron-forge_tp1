package br.unitins.tp1.ironforge.resource;

import java.util.List;

import br.unitins.tp1.ironforge.dto.whey.SaborRequestDTO;
import br.unitins.tp1.ironforge.dto.whey.SaborResponseDTO;
import br.unitins.tp1.ironforge.model.whey.Sabor;
import br.unitins.tp1.ironforge.service.whey.SaborService;
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

@Path("/sabors")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SaborResource {

    @Inject
    public SaborService saborService;

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(SaborResponseDTO.valueOf(saborService.findById(id))).build();
    }

    @GET
    @Path("/search/{nome}")
    public Response findByNome(@PathParam("nome") String nome) {
        List<Sabor> sabores = saborService.findByNome(nome);
        return Response.ok(sabores.stream().map(SaborResponseDTO::valueOf).toList()).build();
    }

    @GET
    public Response findAll() {
        List<Sabor> sabores = saborService.findAll();
        return Response.ok(sabores.stream().map(SaborResponseDTO::valueOf).toList()).build();
    }

    @POST
    public Response create(@Valid SaborRequestDTO dto) {
        return Response.status(Status.CREATED).entity(SaborResponseDTO.valueOf(saborService.create(dto))).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid SaborRequestDTO dto) {
        saborService.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        saborService.delete(id);
        return Response.noContent().build();
    }

}

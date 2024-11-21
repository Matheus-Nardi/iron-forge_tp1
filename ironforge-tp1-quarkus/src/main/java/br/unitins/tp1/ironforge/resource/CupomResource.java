package br.unitins.tp1.ironforge.resource;

import java.util.List;

import br.unitins.tp1.ironforge.dto.cupom.CupomRequestDTO;
import br.unitins.tp1.ironforge.dto.cupom.CupomResponseDTO;
import br.unitins.tp1.ironforge.model.Cupom;
import br.unitins.tp1.ironforge.service.cupom.CupomService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/cupons")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CupomResource {

    @Inject
    public CupomService cupomService;

    @GET
    @Path("/{id}")
    @RolesAllowed("Adm")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(CupomResponseDTO.valueOf(cupomService.findById(id))).build();
    }

    @GET
    @Path("/search/{codigo}")
    @RolesAllowed("Adm")
    public Response findByCodigo(@PathParam("codigo") String codigo) {
        List<Cupom> cupons = cupomService.findByCodigo(codigo);
        return Response.ok(cupons.stream().map(CupomResponseDTO::valueOf).toList()).build();
    }

    @GET
    @Path("/search/{idFabricante}")
    @RolesAllowed("Adm")
    public Response findByFabricante(@PathParam("idFabricante") Long idFabricante) {
        List<Cupom> cupons = cupomService.findByFabricante(idFabricante);
        return Response.ok(cupons.stream().map(CupomResponseDTO::valueOf).toList()).build();
    }

    @GET
    @RolesAllowed("Adm")
    public Response findAll() {
        List<Cupom> cupons = cupomService.findAll();
        return Response.ok(cupons.stream().map(CupomResponseDTO::valueOf).toList()).build();
    }

    @POST
    @RolesAllowed("Adm")
    public Response create(@Valid CupomRequestDTO dto) {
        return Response.status(Status.CREATED).entity(CupomResponseDTO.valueOf(cupomService.create(dto))).build();
    }

    @PUT
    @RolesAllowed("Adm")
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid CupomRequestDTO dto) {
        cupomService.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @RolesAllowed("Adm")
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        cupomService.delete(id);
        return Response.noContent().build();
    }

    @PATCH
    @RolesAllowed("Adm")
    @Path("/{id}")
    public Response deactive(@PathParam("id") Long id) {
        cupomService.deactivate(id);
        return Response.noContent().build();
    }

}

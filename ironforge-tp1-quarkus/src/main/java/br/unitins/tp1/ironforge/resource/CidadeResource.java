package br.unitins.tp1.ironforge.resource;

import java.util.List;

import br.unitins.tp1.ironforge.dto.cidade.CidadeRequestDTO;
import br.unitins.tp1.ironforge.dto.cidade.CidadeResponseDTO;
import br.unitins.tp1.ironforge.model.Cidade;
import br.unitins.tp1.ironforge.service.cidade.CidadeService;
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

@Path("/cidades")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CidadeResource {

    @Inject
    public CidadeService cidadeService;

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(CidadeResponseDTO.valueOf(cidadeService.findById(id))).build();
    }

    @GET
    @Path("/search/{nome}")
    public Response findByNome(@PathParam("nome") String nome) {
        List<Cidade> cidades = cidadeService.findByNome(nome);
        return Response.ok(cidades.stream().map(CidadeResponseDTO::valueOf).toList()).build();
    }

    @GET
    public Response findAll() {
        List<Cidade> cidades = cidadeService.findAll();
        return Response.ok(cidades.stream().map(CidadeResponseDTO::valueOf).toList()).build();
    }

    @POST
    public Response create(@Valid CidadeRequestDTO cidade) {
        return Response.status(Status.CREATED).entity(CidadeResponseDTO.valueOf(cidadeService.create(cidade))).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid CidadeRequestDTO cidade) {
        cidadeService.update(id, cidade);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        cidadeService.delete(id);
        return Response.noContent().build();
    }

}

package br.unitins.tp1.ironforge.resource;

import java.util.List;

import br.unitins.tp1.ironforge.dto.cidade.CidadeRequestDTO;
import br.unitins.tp1.ironforge.model.Cidade;
import br.unitins.tp1.ironforge.service.CidadeService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/cidades")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CidadeResource {

    @Inject
    public CidadeService cidadeService;

    @GET
    @Path("/{id}")
    public Cidade findById(@PathParam("id") Long id) {
        return cidadeService.findById(id);
    }

    @GET
    @Path("/search/{nome}")
    public List<Cidade> findByNome(@PathParam("nome") String nome) {
        return cidadeService.findByNome(nome);
    }

    @GET
    public List<Cidade> findAll() {
        return cidadeService.findAll();
    }

    @POST
    public Cidade create(CidadeRequestDTO cidade) {
        return cidadeService.create(cidade);
    }

    @PUT
    @Path("/{id}")
    public void update(@PathParam("id") Long id, CidadeRequestDTO cidade) {
        cidadeService.update(id, cidade);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        cidadeService.delete(id);
    }

}

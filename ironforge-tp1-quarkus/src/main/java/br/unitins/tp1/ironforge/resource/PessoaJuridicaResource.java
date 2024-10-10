package br.unitins.tp1.ironforge.resource;

import java.util.List;

import br.unitins.tp1.ironforge.dto.usuario.pessoa.PessoaJuridicaRequestDTO;
import br.unitins.tp1.ironforge.dto.usuario.pessoa.PessoaJuridicaResponseDTO;
import br.unitins.tp1.ironforge.model.usuario.PessoaJuridica;
import br.unitins.tp1.ironforge.service.usuario.PessoaJuridicaService;
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

@Path("/pessoasjuridicas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PessoaJuridicaResource {

    @Inject
    public PessoaJuridicaService pessoajuridicaService;

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(PessoaJuridicaResponseDTO.valueOf(pessoajuridicaService.findById(id))).build();
    }

    @GET
    @Path("/search/{nome}")
    public Response findByNome(@PathParam("nome") String nome) {
        List<PessoaJuridica> pessoajuridicas = pessoajuridicaService.findByNome(nome);
        return Response.ok(pessoajuridicas.stream().map(PessoaJuridicaResponseDTO::valueOf).toList()).build();
    }

    @GET
    public Response findAll() {
        List<PessoaJuridica> pessoajuridicas = pessoajuridicaService.findAll();
        return Response.ok(pessoajuridicas.stream().map(PessoaJuridicaResponseDTO::valueOf).toList()).build();
    }

    @POST
    public Response create(@Valid PessoaJuridicaRequestDTO pessoajuridica) {
        return Response.status(Status.CREATED).entity(PessoaJuridicaResponseDTO.valueOf(pessoajuridicaService.create(pessoajuridica))).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid PessoaJuridicaRequestDTO pessoajuridica) {
        pessoajuridicaService.update(id, pessoajuridica);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        pessoajuridicaService.delete(id);
        return Response.noContent().build();
    }

}

package br.unitins.tp1.ironforge.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.tp1.ironforge.dto.cidade.CidadeRequestDTO;
import br.unitins.tp1.ironforge.dto.cidade.CidadeResponseDTO;
import br.unitins.tp1.ironforge.model.Cidade;
import br.unitins.tp1.ironforge.service.cidade.CidadeService;
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

@Path("/cidades")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CidadeResource {

    private static final Logger LOG = Logger.getLogger(CidadeResource.class);

    @Inject
    public CidadeService cidadeService;

    @GET
    @Path("/{id}")
    @RolesAllowed({ "Funcionario", "Administrador" })
    public Response findById(@PathParam("id") Long id) {
        LOG.infof("Execução do método findById. ID da cidade: %d", id);
        return Response.ok(CidadeResponseDTO.valueOf(cidadeService.findById(id))).build();
    }

    @GET
    @Path("/search/{nome}")
    @RolesAllowed({ "Funcionario", "Administrador" })
    public Response findByNome(@PathParam("nome") String nome) {
        LOG.infof("Execução do método findByNome. Nome da cidade: %s", nome);
        List<Cidade> cidades = cidadeService.findByNome(nome);
        return Response.ok(cidades.stream().map(CidadeResponseDTO::valueOf).toList()).build();
    }

    @GET
    @RolesAllowed({ "Funcionario", "Administrador" })
    public Response findAll() {
        LOG.info("Execução do método findAll. Buscando todas as cidades.");
        List<Cidade> cidades = cidadeService.findAll();
        return Response.ok(cidades.stream().map(CidadeResponseDTO::valueOf).toList()).build();
    }

    @POST
    @RolesAllowed({ "Funcionario", "Administrador" })
    public Response create(@Valid CidadeRequestDTO cidade) {
        LOG.infof("Execução do método create. Dados da cidade: %s", cidade);
        return Response.status(Status.CREATED).entity(CidadeResponseDTO.valueOf(cidadeService.create(cidade))).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({ "Funcionario", "Administrador" })
    public Response update(@PathParam("id") Long id, @Valid CidadeRequestDTO cidade) {
        LOG.infof("Execução do método update. Atualizando cidade com ID: %d. Dados: %s", id, cidade);
        cidadeService.update(id, cidade);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({ "Funcionario", "Administrador" })
    public Response delete(@PathParam("id") Long id) {
        LOG.infof("Execução do método delete. Deletando cidade com ID: %d", id);
        cidadeService.delete(id);
        return Response.noContent().build();
    }
}
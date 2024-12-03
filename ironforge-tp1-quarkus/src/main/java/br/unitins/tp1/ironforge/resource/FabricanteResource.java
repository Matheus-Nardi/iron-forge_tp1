package br.unitins.tp1.ironforge.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.tp1.ironforge.dto.pessoajuridica.FabricanteRequestDTO;
import br.unitins.tp1.ironforge.dto.pessoajuridica.FabricanteResponseDTO;
import br.unitins.tp1.ironforge.dto.pessoajuridica.FabricanteUpdateRequestDTO;
import br.unitins.tp1.ironforge.model.Fabricante;
import br.unitins.tp1.ironforge.service.fabricante.FabricanteService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/fabricantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FabricanteResource {

    private static final Logger LOG = Logger.getLogger(FabricanteResource.class);

    @Inject
    public FabricanteService fabricanteService;

    @GET
    @RolesAllowed({ "Administrador", "Funcionario" })
    public Response getAll() {
        LOG.info("Buscando todos os funcionários.");
        List<Fabricante> fabricantes = fabricanteService.findAll();
        return Response.ok(fabricantes.stream().map(FabricanteResponseDTO::valueOf)).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "Administrador", "Funcionario" })
    public Response findById(@PathParam("id") Long id) {
        LOG.infof("Buscando funcionário com ID: %d.", id);
        return Response.ok(FabricanteResponseDTO.valueOf(fabricanteService.findById(id))).build();
    }

    @GET
    @Path("/search/nome")
    @RolesAllowed({ "Administrador", "Funcionario" })
    public Response findByNome(@QueryParam("nome") String nome) {
        LOG.infof("Buscando funcionário com nome: %s.", nome);
        List<Fabricante> fabricantes = fabricanteService.findByNome(nome);
        return Response.ok(fabricantes.stream().map(FabricanteResponseDTO::valueOf).toList()).build();
    }

    @POST
    @RolesAllowed({ "Administrador", "Funcionario" })
    public Response create(@Valid FabricanteRequestDTO fabricanteDTO) {
        LOG.infof("Criando novo funcionário com os dados: %s.", fabricanteDTO);
        Fabricante fabricante = fabricanteService.create(fabricanteDTO);
        return Response.status(Status.CREATED).entity(FabricanteResponseDTO.valueOf(fabricante)).build();
    }

    @PATCH
    @Path("/{id}")
    @RolesAllowed({ "Administrador", "Funcionario" })
    public Response update(@PathParam("id") Long id, @Valid FabricanteUpdateRequestDTO fabricanteToUpdate) {
        LOG.infof("Atualizando funcionário com ID: %d. Novos dados: %s.", id, fabricanteToUpdate);
        fabricanteService.update(id, fabricanteToUpdate);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({ "Administrador", "Funcionario" })
    public Response delete(@PathParam("id") Long id) {
        LOG.infof("Deletando funcionário com ID: %d.", id);
        fabricanteService.delete(id);
        return Response.noContent().build();
    }
}

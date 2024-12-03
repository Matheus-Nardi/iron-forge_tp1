package br.unitins.tp1.ironforge.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.tp1.ironforge.dto.whey.SaborRequestDTO;
import br.unitins.tp1.ironforge.dto.whey.SaborResponseDTO;
import br.unitins.tp1.ironforge.model.whey.Sabor;
import br.unitins.tp1.ironforge.service.whey.sabor.SaborService;
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

@Path("/sabores")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SaborResource {

    private static final Logger LOG = Logger.getLogger(SaborResource.class);

    @Inject
    public SaborService saborService;

    @GET
    @Path("/{id}")
    @RolesAllowed({ "Administrador", "Funcionario" })
    public Response findById(@PathParam("id") Long id) {
        LOG.infof("Buscando sabor com ID: %d", id);
        return Response.ok(SaborResponseDTO.valueOf(saborService.findById(id))).build();
    }

    @GET
    @Path("/search/{nome}")
    @RolesAllowed({ "Administrador", "Funcionario" })
    public Response findByNome(@PathParam("nome") String nome) {
        LOG.infof("Buscando sabores com nome: %s", nome);
        List<Sabor> sabores = saborService.findByNome(nome);
        return Response.ok(sabores.stream().map(SaborResponseDTO::valueOf).toList()).build();
    }

    @GET
    @RolesAllowed({ "Administrador", "Funcionario" })
    public Response findAll() {
        LOG.infof("Buscando todos os sabores");
        List<Sabor> sabores = saborService.findAll();
        return Response.ok(sabores.stream().map(SaborResponseDTO::valueOf).toList()).build();
    }

    @POST
    @RolesAllowed({ "Administrador", "Funcionario" })
    public Response create(@Valid SaborRequestDTO dto) {
        LOG.infof("Criando novo sabor: %s", dto);
        return Response.status(Status.CREATED).entity(SaborResponseDTO.valueOf(saborService.create(dto))).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({ "Administrador", "Funcionario" })
    public Response update(@PathParam("id") Long id, @Valid SaborRequestDTO dto) {
        LOG.infof("Atualizando sabor com ID: %d", id);
        saborService.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({ "Administrador", "Funcionario" })
    public Response delete(@PathParam("id") Long id) {
        LOG.infof("Deletando sabor com ID: %d", id);
        saborService.delete(id);
        return Response.noContent().build();
    }
}
package br.unitins.tp1.ironforge.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.tp1.ironforge.dto.cupom.CupomRequestDTO;
import br.unitins.tp1.ironforge.dto.cupom.CupomResponseDTO;
import br.unitins.tp1.ironforge.model.pedido.Cupom;
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

    private static final Logger LOG = Logger.getLogger(CupomResource.class);

    @Inject
    public CupomService cupomService;

    @GET
    @Path("/{id}")
    @RolesAllowed({ "Funcionario", "Administrador" })
    public Response findById(@PathParam("id") Long id) {
        LOG.infof("Execução do método findById. ID do cupom: %d", id);
        return Response.ok(CupomResponseDTO.valueOf(cupomService.findById(id))).build();
    }

    @GET
    @Path("/search/codigo/{codigo}")
    @RolesAllowed({ "Funcionario", "Administrador" })
    public Response findByCodigo(@PathParam("codigo") String codigo) {
        LOG.infof("Execução do método findByCodigo. Código do cupom: %s", codigo);
        List<Cupom> cupons = cupomService.findByCodigo(codigo);
        return Response.ok(cupons.stream().map(CupomResponseDTO::valueOf).toList()).build();
    }

    @GET
    @Path("/search/fabricante/{idFabricante}")
    @RolesAllowed({ "Funcionario", "Administrador" })
    public Response findByFabricante(@PathParam("idFabricante") Long idFabricante) {
        LOG.infof("Execução do método findByFabricante. ID do fabricante: %d", idFabricante);
        List<Cupom> cupons = cupomService.findByFabricante(idFabricante);
        return Response.ok(cupons.stream().map(CupomResponseDTO::valueOf).toList()).build();
    }

    @GET
    @RolesAllowed({ "Funcionario", "Administrador" })
    public Response findAll() {
        LOG.info("Execução do método findAll. Buscando todos os cupons.");
        List<Cupom> cupons = cupomService.findAll();
        return Response.ok(cupons.stream().map(CupomResponseDTO::valueOf).toList()).build();
    }

    @POST
    @RolesAllowed({ "Funcionario", "Administrador" })
    public Response create(@Valid CupomRequestDTO dto) {
        LOG.infof("Execução do método create. Dados do cupom: %s", dto);
        return Response.status(Status.CREATED).entity(CupomResponseDTO.valueOf(cupomService.create(dto))).build();
    }

    @PUT
    @RolesAllowed({ "Funcionario", "Administrador" })
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid CupomRequestDTO dto) {
        LOG.infof("Execução do método update. Atualizando cupom com ID: %d. Dados: %s", id, dto);
        cupomService.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @RolesAllowed({ "Funcionario", "Administrador" })
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        LOG.infof("Execução do método delete. Deletando cupom com ID: %d", id);
        cupomService.delete(id);
        return Response.noContent().build();
    }

    @PATCH
    @RolesAllowed({ "Funcionario", "Administrador" })
    @Path("/{id}/desativacao")
    public Response deactive(@PathParam("id") Long id) {
        LOG.infof("Execução do método deactive. Desativando cupom com ID: %d", id);
        cupomService.deactivate(id);
        return Response.noContent().build();
    }
}
package br.unitins.tp1.ironforge.resource.endereco;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.tp1.ironforge.dto.estado.EstadoRequestDTO;
import br.unitins.tp1.ironforge.dto.estado.EstadoResponseDTO;
import br.unitins.tp1.ironforge.model.endereco.Estado;
import br.unitins.tp1.ironforge.service.estado.EstadoService;
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

@Path("/estados")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EstadoResource {

    private static final Logger LOG = Logger.getLogger(EstadoResource.class);

    @Inject
    public EstadoService estadoService;

    @GET
    @Path("/{id}")
    @RolesAllowed({ "Administrador", "Funcionario" })
    public Response findById(@PathParam("id") Long id) {
        LOG.infof("Execução do método findById. ID do estado: %d", id);
        Estado estado = estadoService.findById(id);
        LOG.infof("Estado com ID %d encontrado.", id);
        return Response.ok(EstadoResponseDTO.valueOf(estado)).build();
    }

    @GET
    @Path("/search/nome/{nome}")
    @RolesAllowed({ "Administrador", "Funcionario" })
    public Response findByNome(@PathParam("nome") String nome) {
        LOG.infof("Execução do método findByNome. Nome do estado: %s", nome);
        List<Estado> estados = estadoService.findByNome(nome);

        return Response.ok(estados.stream().map(EstadoResponseDTO::valueOf).toList()).build();
    }

    @GET
    @Path("/search/sigla/{sigla}")
    @RolesAllowed({ "Administrador", "Funcionario" })
    public Response findBySigla(@PathParam("sigla") String sigla) {
        LOG.infof("Execução do método findBySigla. Sigla do estado: %s", sigla);
        Estado estado = estadoService.findBySigla(sigla);
        return Response.ok(EstadoResponseDTO.valueOf(estado)).build();
    }

    @GET
    @RolesAllowed({ "Administrador", "Funcionario" })
    public Response findAll() {
        LOG.info("Execução do método findAll. Buscando todos os estados.");
        List<Estado> estados = estadoService.findAll();

        return Response.ok(estados.stream().map(EstadoResponseDTO::valueOf).toList()).build();
    }

    @POST
    @RolesAllowed({ "Administrador", "Funcionario" })
    public Response create(@Valid EstadoRequestDTO estadoDTO) {
        LOG.infof("Execução do método create. Dados do estado: %s", estadoDTO);
        Estado estado = estadoService.create(estadoDTO);
        return Response.status(Status.CREATED).entity(EstadoResponseDTO.valueOf(estado)).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({ "Administrador", "Funcionario" })
    public Response update(@PathParam("id") Long id, @Valid EstadoRequestDTO estadoDTO) {
        LOG.infof("Execução do método update. Atualizando estado com ID: %d. Dados: %s", id, estadoDTO);
        estadoService.update(id, estadoDTO);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({ "Administrador", "Funcionario" })
    public Response delete(@PathParam("id") Long id) {
        LOG.infof("Execução do método delete. Deletando estado com ID: %d", id);
        estadoService.delete(id);
        return Response.noContent().build();
    }
}

package br.unitins.tp1.ironforge.resource;

import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import br.unitins.tp1.ironforge.dto.pagamento.CartaoRequestDTO;
import br.unitins.tp1.ironforge.dto.pagamento.CartaoResponseDTO;
import br.unitins.tp1.ironforge.model.pagamento.Cartao;
import br.unitins.tp1.ironforge.service.CartaoService;
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

@Path("/cartoes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CartaoResource {

    private static final Logger LOG = Logger.getLogger(CartaoResource.class);

    @Inject
    public CartaoService cartaoService;

    @Inject
    public JsonWebToken jsonWebToken;

    @GET
    @Path("/{id}")
    @RolesAllowed({ "Administrador", "Cliente" })
    public Response findById(@PathParam("id") Long id) {
        LOG.infof("Execução do método findById. ID do cartão: %d", id);
        return Response.ok(CartaoResponseDTO.valueOf(cartaoService.findById(id))).build();
    }

    @GET
    @RolesAllowed({ "Administrador", "Cliente" })
    public Response findByCliente() {
        String username = jsonWebToken.getSubject();
        LOG.infof("Execução do método findByCliente. Usuário: %s", username);
        List<Cartao> cartoes = cartaoService.findByCliente(username);
        return Response.ok(cartoes.stream().map(CartaoResponseDTO::valueOf).toList()).build();
    }

    @GET
    @RolesAllowed({ "Administrador" })
    public Response findAll() {
        LOG.info("Execução do método findAll. Buscando todos os cartões.");
        List<Cartao> cartaoes = cartaoService.findAll();
        return Response.ok(cartaoes.stream().map(CartaoResponseDTO::valueOf).toList()).build();
    }

    @POST
    @RolesAllowed({ "Administrador", "Cliente" })
    public Response create(@Valid CartaoRequestDTO dto) {
        String username = jsonWebToken.getSubject();
        LOG.infof("Execução do método create. Usuário: %s. Dados do cartão: %s", username, dto);
        return Response.status(Status.CREATED).entity(CartaoResponseDTO.valueOf(cartaoService.create(username, dto)))
                .build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({ "Administrador", "Cliente" })
    public Response update(@PathParam("id") Long id, @Valid CartaoRequestDTO dto) {
        String username = jsonWebToken.getSubject();
        LOG.infof("Execução do método update. Usuário: %s. Atualizando cartão com ID: %d. Dados: %s", username, id,
                dto);
        cartaoService.update(username, id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({ "Administrador", "Cliente" })
    public Response delete(@PathParam("id") Long id) {
        String username = jsonWebToken.getSubject();
        LOG.infof("Execução do método delete. Usuário: %s. Deletando cartão com ID: %d", username, id);
        cartaoService.delete(username, id);
        return Response.noContent().build();
    }
}
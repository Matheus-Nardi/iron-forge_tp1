package br.unitins.tp1.ironforge.resource;

import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;

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

    @Inject
    public CartaoService cartaoService;

    @Inject
    public JsonWebToken jsonWebToken;

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(CartaoResponseDTO.valueOf(cartaoService.findById(id))).build();
    }

    @GET
    @RolesAllowed("User")
    public Response findByCliente() {
        String username = jsonWebToken.getSubject();
        List<Cartao> cartoes = cartaoService.findByCliente(username);
        return Response.ok(cartoes.stream().map(CartaoResponseDTO::valueOf).toList()).build();
    }

    @GET
    public Response findAll() {
        List<Cartao> cartaoes = cartaoService.findAll();
        return Response.ok(cartaoes.stream().map(CartaoResponseDTO::valueOf).toList()).build();
    }

    @POST
    @RolesAllowed("User")
    public Response create(@Valid CartaoRequestDTO dto) {
        String username = jsonWebToken.getSubject();
        return Response.status(Status.CREATED).entity(CartaoResponseDTO.valueOf(cartaoService.create(username, dto)))
                .build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("User")
    public Response update(@PathParam("id") Long id, @Valid CartaoRequestDTO dto) {
        String username = jsonWebToken.getSubject();
        cartaoService.update(username, id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("User")
    public Response delete(@PathParam("id") Long id) {
        String username = jsonWebToken.getSubject();
        cartaoService.delete(username, id);
        return Response.noContent().build();
    }

}

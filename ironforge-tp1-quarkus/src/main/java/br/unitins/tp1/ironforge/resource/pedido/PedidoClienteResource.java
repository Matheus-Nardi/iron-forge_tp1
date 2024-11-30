package br.unitins.tp1.ironforge.resource.pedido;

import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.unitins.tp1.ironforge.dto.pedido.PedidoRequestDTO;
import br.unitins.tp1.ironforge.dto.pedido.PedidoBasicoResponseDTO;
import br.unitins.tp1.ironforge.dto.pedido.PedidoDetalhadoResponseDTO;
import br.unitins.tp1.ironforge.model.pedido.Pedido;
import br.unitins.tp1.ironforge.service.pedido.PedidoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoClienteResource {

    @Inject
    public PedidoService pedidoService;

    @Inject
    public JsonWebToken jsonWebToken;

    @GET
    @Path("/historico")
    @RolesAllowed({ "User" })
    public Response findByUsername() {
        String username = jsonWebToken.getSubject();
        List<Pedido> pedidos = pedidoService.findByUsername(username);
        return Response.ok(pedidos.stream().map(PedidoBasicoResponseDTO::valueOf)).build();
    }

    @GET
    @Path("/{id}/detalhes")
    @RolesAllowed({ "User" })
    public Response findDetalhesByPedido(@PathParam("id") Long id) {
        String username = jsonWebToken.getSubject();
        return Response.ok(PedidoDetalhadoResponseDTO.valueOf(pedidoService.detailsPedido(id, username))).build();
    }

    @POST
    @RolesAllowed({ "User" })
    public Response create(PedidoRequestDTO pedidoDTO) {
        String username = jsonWebToken.getSubject();
        Pedido pedido = pedidoService.create(pedidoDTO, username);
        return Response.status(Status.CREATED).entity(PedidoBasicoResponseDTO.valueOf(pedido)).build();
    }

    @PATCH
    @Path("/cancelamento/{id}")
    @RolesAllowed("User")
    public Response cancel(@PathParam("id") Long id) {
        String username = jsonWebToken.getSubject();
        pedidoService.cancelPedido(username, id);
        return Response.noContent().build();
    }

}
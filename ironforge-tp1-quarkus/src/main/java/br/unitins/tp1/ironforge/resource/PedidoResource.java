package br.unitins.tp1.ironforge.resource;

import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.unitins.tp1.ironforge.dto.pedido.PedidoRequestDTO;
import br.unitins.tp1.ironforge.dto.pedido.PedidoResponseDTO;
import br.unitins.tp1.ironforge.model.pedido.Pedido;
import br.unitins.tp1.ironforge.service.pedido.PedidoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoResource {

    @Inject
    public PedidoService pedidoService;

    @Inject
    public JsonWebToken jsonWebToken;

    @GET
    @RolesAllowed("User")
    public Response findByUsername() {

        String username = jsonWebToken.getSubject();
        List<Pedido> pedidos = pedidoService.findByUsername(username);
        return Response.ok(pedidos.stream().map(PedidoResponseDTO::valueOf)).build();
    }

    @POST
    @RolesAllowed("User")
    public Response create(PedidoRequestDTO pedidoDTO) {
        String username = jsonWebToken.getSubject();
        Pedido pedido = pedidoService.create(pedidoDTO, username);
        return Response.status(Status.CREATED).entity(PedidoResponseDTO.valueOf(pedido)).build();
    }

}

package br.unitins.tp1.ironforge.resource.pedido;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import br.unitins.tp1.ironforge.model.pedido.Situacao;
import br.unitins.tp1.ironforge.service.pedido.PedidoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/adm/pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoAdministrativoResource {

    private static final Logger LOG = Logger.getLogger(PedidoAdministrativoResource.class);

    @Inject
    public PedidoService pedidoService;

    @Inject
    public JsonWebToken jsonWebToken;

    @PATCH
    @Path("/{id}")
    @RolesAllowed({ "Funcionario", "Administrador" })
    public Response updateStatusPedido(@PathParam("id") Long id, @QueryParam("situacao") Situacao situacao) {
        LOG.infof("Atualizado status do pedido %d para %s", id, situacao.getLabel());
        pedidoService.updateStatusPedido(id, situacao);
        return Response.noContent().build();
    }

}
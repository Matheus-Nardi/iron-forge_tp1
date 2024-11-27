package br.unitins.tp1.ironforge.resource;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.unitins.tp1.ironforge.dto.pagamento.BoletoResponseDTO;
import br.unitins.tp1.ironforge.dto.pagamento.PixResponseDTO;
import br.unitins.tp1.ironforge.model.pagamento.TipoPagamento;
import br.unitins.tp1.ironforge.service.PagamentoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/pagamentos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PagamentoResource {

    @Inject
    public PagamentoService pagamentoService;

    @Inject
    public JsonWebToken jsonWebToken;

    @POST
    @Path("/{idPedido}/informacoes/pix")
    @RolesAllowed("User")
    public Response gerarPix(@PathParam("idPedido") Long idPedido) {
        String username = jsonWebToken.getSubject();
        return Response.status(Status.CREATED)
                .entity(PixResponseDTO.valueOf(pagamentoService.gerarPix(idPedido, username))).build();
    }

    @POST
    @Path("/{idPedido}/informacoes/boleto")
    @RolesAllowed("User")
    public Response gerarBoleto(@PathParam("idPedido") Long idPedido) {
        String username = jsonWebToken.getSubject();
        return Response.status(Status.CREATED)
                .entity(BoletoResponseDTO.valueOf(pagamentoService.gerarBoleto(idPedido, username))).build();
    }

    @PATCH
    @Path("/{idPedido}/pix/{chave}")
    @RolesAllowed("User")
    public Response pagarPix(@PathParam("idPedido") Long idPedido, @PathParam("chave") String chave) {
        String username = jsonWebToken.getSubject();
        pagamentoService.pagar(idPedido, username, chave, TipoPagamento.PIX);
        return Response.noContent().build();
    }

    @PATCH
    @Path("/{idPedido}/boleto/{codigo}")
    @RolesAllowed("User")
    public Response pagarBoleto(@PathParam("idPedido") Long idPedido, @PathParam("codigo") String codigo) {
        String username = jsonWebToken.getSubject();
        pagamentoService.pagar(idPedido, username, codigo, TipoPagamento.BOLETO);
        return Response.noContent().build();
    }

    @PATCH
    @Path("/{idPedido}/cartao/{idCartao}")
    @RolesAllowed("User")
    public Response pagarCartao(@PathParam("idPedido") Long idPedido, @PathParam("idCartao") Long idCartao) {
        String username = jsonWebToken.getSubject();
        pagamentoService.pagarCartao(idPedido, username, idCartao);
        return Response.noContent().build();
    }
}

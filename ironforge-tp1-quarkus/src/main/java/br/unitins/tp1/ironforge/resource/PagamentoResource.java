package br.unitins.tp1.ironforge.resource;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

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

    private static final Logger LOG = Logger.getLogger(PagamentoResource.class);

    @Inject
    public PagamentoService pagamentoService;

    @Inject
    public JsonWebToken jsonWebToken;

    @POST
    @Path("/{idPedido}/informacoes/pix")
    @RolesAllowed({ "Cliente", "Administrador" })
    public Response gerarPix(@PathParam("idPedido") Long idPedido) {
        String username = jsonWebToken.getSubject();
        LOG.infof("Gerando informações de pagamento PIX para o pedido %d pelo usuário %s", idPedido, username);
        return Response.status(Status.CREATED)
                .entity(PixResponseDTO.valueOf(pagamentoService.gerarPix(idPedido, username))).build();
    }

    @POST
    @Path("/{idPedido}/informacoes/boleto")
    @RolesAllowed({ "Cliente", "Administrador" })
    public Response gerarBoleto(@PathParam("idPedido") Long idPedido) {
        String username = jsonWebToken.getSubject();
        LOG.infof("Gerando informações de pagamento Boleto para o pedido %d pelo usuário %s", idPedido, username);
        return Response.status(Status.CREATED)
                .entity(BoletoResponseDTO.valueOf(pagamentoService.gerarBoleto(idPedido, username))).build();
    }

    @PATCH
    @Path("/{idPedido}/pix/{chave}")
    @RolesAllowed({ "Cliente", "Administrador" })
    public Response pagarPix(@PathParam("idPedido") Long idPedido, @PathParam("chave") String chave) {
        String username = jsonWebToken.getSubject();
        LOG.infof("Realizando pagamento via PIX para o pedido %d com chave %s pelo usuário %s", idPedido, chave,
                username);
        pagamentoService.pagar(idPedido, username, chave, TipoPagamento.PIX);
        return Response.noContent().build();
    }

    @PATCH
    @Path("/{idPedido}/boleto/{codigo}")
    @RolesAllowed({ "Cliente", "Administrador" })
    public Response pagarBoleto(@PathParam("idPedido") Long idPedido, @PathParam("codigo") String codigo) {
        String username = jsonWebToken.getSubject();
        LOG.infof("Realizando pagamento via Boleto para o pedido %d com código %s pelo usuário %s", idPedido, codigo,
                username);
        pagamentoService.pagar(idPedido, username, codigo, TipoPagamento.BOLETO);
        return Response.noContent().build();
    }

    @PATCH
    @Path("/{idPedido}/cartao/{idCartao}")
    @RolesAllowed({ "Cliente", "Administrador" })
    public Response pagarCartao(@PathParam("idPedido") Long idPedido, @PathParam("idCartao") Long idCartao) {
        String username = jsonWebToken.getSubject();
        LOG.infof("Realizando pagamento via Cartão para o pedido %d com cartão %d pelo usuário %s", idPedido, idCartao,
                username);
        pagamentoService.pagarCartao(idPedido, username, idCartao);
        return Response.noContent().build();
    }
}

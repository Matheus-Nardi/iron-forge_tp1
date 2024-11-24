package br.unitins.tp1.ironforge.service;

import java.time.LocalDateTime;
import java.util.UUID;

import br.unitins.tp1.ironforge.model.pagamento.Boleto;
import br.unitins.tp1.ironforge.model.pagamento.Pagamento;
import br.unitins.tp1.ironforge.model.pagamento.Pix;
import br.unitins.tp1.ironforge.model.pagamento.TipoPagamento;
import br.unitins.tp1.ironforge.model.pedido.Pedido;
import br.unitins.tp1.ironforge.model.pedido.Situacao;
import br.unitins.tp1.ironforge.model.usuario.Cliente;
import br.unitins.tp1.ironforge.repository.PagamentoRepository;
import br.unitins.tp1.ironforge.service.pedido.PedidoService;
import br.unitins.tp1.ironforge.service.usuario.ClienteService;
import br.unitins.tp1.ironforge.validation.EntidadeNotFoundException;
import br.unitins.tp1.ironforge.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class PagamentoServiceImpl implements PagamentoService {

    @Inject
    public PagamentoRepository pagamentoRepository;

    @Inject
    public ClienteService clienteService;

    @Inject
    public PedidoService pedidoService;

    @Override
    public Pagamento findById(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id);
        if (pagamento == null) {
            throw new EntidadeNotFoundException("id", "Pagamento não encontrado");
        }
        return pagamento;
    }


    @Override
    @Transactional
    public Pix gerarPix(Long idPedido, String username) {
        Pedido pedido = pedidoService.findById(idPedido);
        Cliente cliente = pedido.getCliente();
        if (!(cliente.equals(clienteService.findByUsuario(username)))) {
            throw new ValidationException("idPedido", "Cliente não possui um pedido com esse codigo");
        }

        Pix pix = new Pix();
        pix.setChave(gerarUUIDPedidoCliente(idPedido, cliente.getId()));
        pix.setDestinatario("Iron Forge");
        pix.setDataVencimento(pedido.getData().plusHours(3));
        pix.setValor(pedido.getValorTotal());
        pix.setPago(false);
        pix.setTipoPagamento(TipoPagamento.PIX);

        pagamentoRepository.persist(pix);
        pedido.setPagamento(pix);

        return pix;

    }

    @Override
    @Transactional
    public Boleto gerarBoleto(Long idPedido, String username) {
        Pedido pedido = pedidoService.findById(idPedido);
        Cliente cliente = pedido.getCliente();
        if (!(cliente.equals(clienteService.findByUsuario(username)))) {
            throw new ValidationException("idPedido", "Cliente não possui um pedido com esse codigo");
        }

        Boleto boleto = new Boleto();
        boleto.setCodigoBarras(gerarUUIDPedidoCliente(idPedido, cliente.getId()));
        boleto.setValor(pedido.getValorTotal());
        boleto.setDataVencimento(pedido.getData().plusDays(3));
        boleto.setPago(false);
        boleto.setTipoPagamento(TipoPagamento.BOLETO);

        pagamentoRepository.persist(boleto);
        pedido.setPagamento(boleto);

        return boleto;
    }

    @Override
    @Transactional
    public void pagar(Long idPedido, String username, String identificador, TipoPagamento tipoPagamento) {
        Pedido pedido = pedidoService.findById(idPedido);
        Cliente cliente = pedido.getCliente();

        if (!(cliente.equals(clienteService.findByUsuario(username)))) {
            throw new ValidationException("idPedido", "Cliente não possui um pedido com esse código");
        }

        Pagamento pagamento;
        switch (tipoPagamento) {
            case PIX:
                pagamento = pagamentoRepository.findByChavePix(identificador);
                break;
            case BOLETO:
                pagamento = pagamentoRepository.findByCodigoBoleto(identificador);
                break;
            default:
                throw new IllegalArgumentException("Tipo de pagamento inválido");
        }

        if (!isPagamentoValido(pagamento)) {
            pedidoService.updateStatusPedido(idPedido, Situacao.CANCELADO);
            throw new ValidationException("idPedido",
                    "O prazo para realizar o pagamento expirou. O pedido foi cancelado");
        }

        if (!(pagamento.equals(pedido.getPagamento()))) {
            throw new ValidationException("identificador", "O identificador fornecido é inválido");
        }

        if (pagamento.getPago()) {
            throw new ValidationException("identificador", "Pagamento já realizado");
        }

        pagamento.setDataPagamento(LocalDateTime.now());
        pagamento.setPago(true);
        pedidoService.updateStatusPedido(idPedido, Situacao.SEPARANDO_PEDIDO);
    }

    private String gerarUUIDPedidoCliente(Long idPedido, Long idCliente) {
        String base = idPedido + "-" + idCliente;
        return UUID.nameUUIDFromBytes(base.getBytes()).toString();
    }

    private boolean isPagamentoValido(Pagamento pagamento) {
        return pagamento.getDataVencimento().isAfter(LocalDateTime.now());
    }

}

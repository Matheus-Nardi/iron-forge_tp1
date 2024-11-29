package br.unitins.tp1.ironforge.service;

import java.time.LocalDateTime;
import java.util.UUID;

import br.unitins.tp1.ironforge.model.pagamento.Boleto;
import br.unitins.tp1.ironforge.model.pagamento.Cartao;
import br.unitins.tp1.ironforge.model.pagamento.CartaoPagamento;
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

    @Inject
    public CartaoService cartaoService;

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
        validarCliente(username, cliente);
        if (pedido.getStatusPedidos().stream().anyMatch(s -> s.getSituacao() == Situacao.CANCELADO)) {
            throw new ValidationException("idPedido", "O pedido já foi cancelado, não é possível pagar");
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
        validarCliente(username, cliente);
        if (pedido.getStatusPedidos().stream().anyMatch(s -> s.getSituacao() == Situacao.CANCELADO)) {
            throw new ValidationException("idPedido", "O pedido já foi cancelado, não é possível pagar");
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

        validarCliente(username, cliente);

        Pagamento pagamento = null;

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

        validarPagamentoPedido(idPedido, pedido, pagamento);
        pagamento.setDataPagamento(LocalDateTime.now());
        pagamento.setPago(true);
        pedidoService.updateStatusPedido(idPedido, Situacao.SEPARANDO_PEDIDO);
    }

    @Override
    @Transactional
    public void pagarCartao(Long idPedido, String username, Long idCartao) {
        Pedido pedido = pedidoService.findById(idPedido);

        if (pedido.getPagamento() != null && pedido.getPagamento().getPago()) {
            throw new ValidationException("idPedido", "O pedido já foi pago. Não é possível realizar outro pagamento.");
        }
        Cliente cliente = pedido.getCliente();

        validarCliente(username, cliente);

        Cartao cartao = cartaoService.findById(idCartao);
        if (!cliente.getCartoes().contains(cartao)) {
            throw new EntidadeNotFoundException("idCartao", "Cartão não encontrado");
        }

        CartaoPagamento cartaoPagamento = new CartaoPagamento();

        cartaoPagamento.setCpf(cartao.getCpf());
        cartaoPagamento.setCvc(cartao.getCvc());
        cartaoPagamento.setValidade(cartao.getValidade());
        cartaoPagamento.setNumero(cartao.getNumero());
        cartaoPagamento.setTitular(cartao.getTitular());
        cartaoPagamento.setValor(pedido.getValorTotal());
        cartaoPagamento.setTipoCartao(cartao.getTipoCartao());
        cartaoPagamento.setDataPagamento(LocalDateTime.now());
        cartaoPagamento.setPago(true);
        cartaoPagamento.setTipoPagamento(TipoPagamento.CARTAO);
        pedido.setPagamento(cartaoPagamento);
        validarPagamentoPedido(idPedido, pedido, cartaoPagamento);
        pedidoService.updateStatusPedido(idPedido, Situacao.SEPARANDO_PEDIDO);
    }

    private void validarPagamentoPedido(Long idPedido, Pedido pedido, Pagamento pagamento) {
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

        // Adicionar uma lógica para caso o pedido ja esteja cancelado
        if (pedido.getStatusPedidos().stream().anyMatch(s -> s.getSituacao() == Situacao.CANCELADO)) {
            throw new ValidationException("idPedido", "O pedido já foi cancelado, não é possível pagar");
        }

    }

    private void validarCliente(String username, Cliente cliente) {
        if (!(cliente.equals(clienteService.findByUsuario(username)))) {
            throw new ValidationException("idPedido", "Cliente não possui um pedido com esse código");
        }
    }

    private String gerarUUIDPedidoCliente(Long idPedido, Long idCliente) {
        String base = idPedido + "-" + idCliente;
        return UUID.nameUUIDFromBytes(base.getBytes()).toString();
    }

    private boolean isPagamentoValido(Pagamento pagamento) {
        return pagamento.getDataVencimento().isAfter(LocalDateTime.now());
    }

}

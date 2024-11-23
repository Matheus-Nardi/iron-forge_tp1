package br.unitins.tp1.ironforge.service.pedido;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.unitins.tp1.ironforge.dto.itempedido.ItemPedidoRequestDTO;
import br.unitins.tp1.ironforge.dto.pedido.PedidoRequestDTO;
import br.unitins.tp1.ironforge.model.Cupom;
import br.unitins.tp1.ironforge.model.ItemPedido;
import br.unitins.tp1.ironforge.model.Lote;
import br.unitins.tp1.ironforge.model.pagamento.Boleto;
import br.unitins.tp1.ironforge.model.pagamento.Pagamento;
import br.unitins.tp1.ironforge.model.pagamento.Pix;
import br.unitins.tp1.ironforge.model.pagamento.StatusPagamento;
import br.unitins.tp1.ironforge.model.pedido.EnderecoEntrega;
import br.unitins.tp1.ironforge.model.pedido.Pedido;
import br.unitins.tp1.ironforge.model.pedido.Situacao;
import br.unitins.tp1.ironforge.model.pedido.StatusPedido;
import br.unitins.tp1.ironforge.model.usuario.Cliente;
import br.unitins.tp1.ironforge.repository.PagamentoRepository;
import br.unitins.tp1.ironforge.repository.PedidoRepository;
import br.unitins.tp1.ironforge.service.cidade.CidadeService;
import br.unitins.tp1.ironforge.service.cupom.CupomService;
import br.unitins.tp1.ironforge.service.lote.LoteService;
import br.unitins.tp1.ironforge.service.usuario.ClienteService;
import br.unitins.tp1.ironforge.service.usuario.UsuarioService;
import br.unitins.tp1.ironforge.validation.EntidadeNotFoundException;
import br.unitins.tp1.ironforge.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class PedidoServiceImpl implements PedidoService {

    @Inject
    public PedidoRepository pedidoRepository;

    @Inject
    public UsuarioService usuarioService;

    @Inject
    public ClienteService clienteService;

    @Inject
    public LoteService loteService;

    @Inject
    public CupomService cupomService;

    @Inject
    public CidadeService cidadeService;

    @Inject
    public PagamentoRepository pagamentoRepository;

    @Override
    public Pedido findById(Long id) {
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null) {
            throw new EntidadeNotFoundException("id", "Pedido não encontrado");
        }
        return pedidoRepository.findById(id);
    }

    @Override
    @Transactional
    public Pedido create(PedidoRequestDTO dto, String username) {
        Pedido pedido = new Pedido();
        pedido.setData(LocalDateTime.now());
        pedido.setCliente(clienteService.findByUsuario(username));
        pedido.setEnderecoEntrega(getEnderecoEntrega(dto));
        obterCupom(dto, pedido);

        pedido.setItensPedidos(new ArrayList<>());
        definirItens(dto, pedido);

        Double valorFinal = arredondarParaDuasCasasDecimais(aplicarDesconto(pedido));
        if (!(dto.valorTotal().equals(valorFinal)))
            throw new ValidationException("valorTotal", "O valor fornecido não corresponde ao valor final do pedido!");
        pedido.setValorTotal(valorFinal);
        getStatusPedido(pedido);

        pedidoRepository.persist(pedido);

        return pedido;
    }

    private EnderecoEntrega getEnderecoEntrega(PedidoRequestDTO dto) {
        EnderecoEntrega enderecoEntrega = new EnderecoEntrega();
        enderecoEntrega.setBairro(dto.enderecoEntrega().bairro());
        enderecoEntrega.setLogradouro(dto.enderecoEntrega().logradouro());
        enderecoEntrega.setCep(dto.enderecoEntrega().cep());
        enderecoEntrega.setNumero(dto.enderecoEntrega().numero());
        enderecoEntrega.setComplemento(dto.enderecoEntrega().complemento());
        enderecoEntrega.setCidade(cidadeService.findById(dto.enderecoEntrega().idCidade()));
        return enderecoEntrega;
    }

    private void getStatusPedido(Pedido pedido) {
        pedido.setStatusPedidos(new ArrayList<>());
        StatusPedido statusPedido = new StatusPedido();
        statusPedido.setSituacao(Situacao.AGURARDANDO_PAGAMENTO);
        statusPedido.setDataAtualizacao(LocalDateTime.now());
        pedido.getStatusPedidos().add(statusPedido);
    }

    private void definirItens(PedidoRequestDTO dto, Pedido pedido) {
        for (ItemPedidoRequestDTO itemDTO : dto.itensPedidos()) {
            ItemPedido item = new ItemPedido();
            Lote lote = loteService.findByWhey(itemDTO.idProduto());
            item.setLote(lote);
            item.setPreco(itemDTO.preco());
            item.setQuantidade(itemDTO.quantidade());

            lote.setQuantidade(lote.getQuantidade() - itemDTO.quantidade());

            pedido.getItensPedidos().add(item);
        }
    }

    private void obterCupom(PedidoRequestDTO dto, Pedido pedido) {
        if (dto.cupom() == null || dto.cupom().isBlank()) {
            pedido.setCupom(null);
            return;
        } else {
            List<Cupom> cupons = cupomService.findByCodigo(dto.cupom());
            if (cupons.isEmpty()) {
                throw new ValidationException("cupom", "Cupom não encontrado ou inválido");
            }

            if (!cupons.get(0).isValido() || (cupons.get(0).getAtivo().equals(false))) {
                throw new ValidationException("cupom", "Cupom não encontrado ou inválido");
            }

            pedido.setCupom(cupons.get(0));
        }
    }

    private Double calcularTotal(List<ItemPedido> itensPedidos) {
        if (itensPedidos == null || itensPedidos.isEmpty()) {
            return 0.0;
        }
        Double total = 0.0;
        for (ItemPedido itemPedido : itensPedidos) {
            total = total + itemPedido.getPreco() * itemPedido.getQuantidade();
        }

        return total;
    }

    private Double aplicarDesconto(Pedido pedido) {
        Double total = calcularTotal(pedido.getItensPedidos());
        if (pedido.getCupom() == null)
            return total;

        return total - (pedido.getCupom().getPercentualDesconto() * total);
    }

    private Double arredondarParaDuasCasasDecimais(Double valor) {
        if (valor == null) {
            return 0.0;
        }

        return Math.round(valor * 100.0) / 100.0;
    }

    @Override
    public List<Pedido> findByUsername(String username) {
        Cliente cliente = clienteService.findByUsuario(username);
        return pedidoRepository.findByCliente(cliente.getId());
    }

    @Override
    @Transactional
    public void updateStatusPedido(Long id, Situacao situacao) {
        Pedido pedido = pedidoRepository.findById(id);

        if (pedido == null) {
            throw new EntidadeNotFoundException("id", "Pedido não encontrado");
        }

        StatusPedido status = new StatusPedido();
        status.setSituacao(situacao);
        status.setDataAtualizacao(LocalDateTime.now());
        pedido.getStatusPedidos().add(status);
    }

    @Override
    public Pix gerarPix(Long id) {
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null) {
            throw new EntidadeNotFoundException("id", "Pedido não encontrado");
        }
        Pix pix = new Pix();
        pix.setDataVencimento(pedido.getData().plusMinutes(30));
        pix.setChave(UUID.randomUUID().toString());
        pix.setDestinatario("Iron Forge");
        pix.setStatusPagamento(StatusPagamento.PENDENTE);
        pix.setValor(pedido.getValorTotal());
        pagamentoRepository.persist(pix);
        return pix;

    }

    @Override
    public Boleto gerarBoleto(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'gerarBoleto'");
    }

    @Override
    public void pagar(Pagamento pagamento) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pagar'");
    }

}

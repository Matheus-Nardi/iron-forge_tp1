package br.unitins.tp1.ironforge.service.pedido;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.tp1.ironforge.dto.itempedido.ItemPedidoRequestDTO;
import br.unitins.tp1.ironforge.dto.pedido.PedidoRequestDTO;
import br.unitins.tp1.ironforge.model.Cupom;
import br.unitins.tp1.ironforge.model.Endereco;
import br.unitins.tp1.ironforge.model.ItemPedido;
import br.unitins.tp1.ironforge.model.Lote;
import br.unitins.tp1.ironforge.model.pedido.EnderecoEntrega;
import br.unitins.tp1.ironforge.model.pedido.Pedido;
import br.unitins.tp1.ironforge.model.pedido.Situacao;
import br.unitins.tp1.ironforge.model.pedido.StatusPedido;
import br.unitins.tp1.ironforge.model.usuario.Cliente;
import br.unitins.tp1.ironforge.repository.PagamentoRepository;
import br.unitins.tp1.ironforge.repository.PedidoRepository;
import br.unitins.tp1.ironforge.service.cidade.CidadeService;
import br.unitins.tp1.ironforge.service.cupom.CupomService;
import br.unitins.tp1.ironforge.service.estado.EstadoService;
import br.unitins.tp1.ironforge.service.lote.LoteService;
import br.unitins.tp1.ironforge.service.usuario.ClienteService;
import br.unitins.tp1.ironforge.service.usuario.UsuarioService;
import br.unitins.tp1.ironforge.validation.EntidadeNotFoundException;
import br.unitins.tp1.ironforge.validation.ValidationException;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class PedidoServiceImpl implements PedidoService {

    private static final Logger LOG = Logger.getLogger(PedidoServiceImpl.class);
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
    public EstadoService estadoService;

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
        Cliente cliente = clienteService.findByUsuario(username);
        pedido.setData(LocalDateTime.now());
        pedido.setCliente(cliente);
        pedido.setEnderecoEntrega(getEnderecoEntrega(cliente, dto.idEndereco()));
        obterCupom(dto, pedido);

        pedido.setItensPedidos(new ArrayList<>());
        definirItens(dto, pedido);

        Double valorFinal = arredondarParaDuasCasasDecimais(aplicarDesconto(pedido));
        LOG.info("Valor final: " + valorFinal);
        if (!(dto.valorTotal().equals(valorFinal)))
            throw new ValidationException("valorTotal", "O valor fornecido não corresponde ao valor final do pedido!");

        pedido.setValorTotal(valorFinal);
        getStatusPedido(pedido);

        pedidoRepository.persist(pedido);

        return pedido;
    }

    private EnderecoEntrega getEnderecoEntrega(Cliente cliente, Long idEndereco) {
        Endereco endereco = cliente.getPessoaFisica().getEnderecos().stream().filter(e -> e.getId().equals(idEndereco))
                .findFirst()
                .orElseThrow(() -> new EntidadeNotFoundException("idEndereco", "Endereco não encontrado"));
        EnderecoEntrega enderecoEntrega = new EnderecoEntrega();
        enderecoEntrega.setBairro(endereco.getBairro());
        enderecoEntrega.setLogradouro(endereco.getLogradouro());
        enderecoEntrega.setCep(endereco.getCep());
        enderecoEntrega.setNumero(endereco.getNumero());
        enderecoEntrega.setComplemento(endereco.getComplemento());
        enderecoEntrega.setCidade(endereco.getCidade());
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
            item.setPreco(0.0);
            Lote lote = loteService.findByWhey(itemDTO.idProduto());

            int qtdeTotalEstoque = loteService.findByIdWheyQuantTotal(itemDTO.idProduto());
            if (qtdeTotalEstoque < itemDTO.quantidade())
                throw new ValidationException("quantidade", "quantidade em estoque insuficiente");

            int qtdeRestante = itemDTO.quantidade();

            while (qtdeRestante > 0) {
                lote = loteService.findByWhey(itemDTO.idProduto());

                int qtdeConsumida = Math.min(lote.getQuantidade(), qtdeRestante);
                lote.setQuantidade(lote.getQuantidade() - qtdeConsumida);
                LOG.info(lote.getQuantidade());
                item.setPreco(item.getPreco() + (qtdeConsumida * lote.getWheyProtein().getPreco()));

                qtdeRestante -= qtdeConsumida;
            }
            item.setQuantidade(itemDTO.quantidade());
            item.setLote(lote);
            item.setPreco(lote.getWheyProtein().getPreco());
            item.setQuantidade(itemDTO.quantidade());

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

    @Transactional
    @Scheduled(every = "2m")
    public void verifyIfPaymentIsNull() {
        List<Pedido> pedidos = pedidoRepository.findPedidoWherePagamentoIsNullAndNotCanceled();

        pedidos.forEach(p -> {
            if (LocalDateTime.now().isAfter(p.getData().plusMinutes(10))) {
                pedidoRepository.delete(p);
            }
            // devolverLote(p); Não está funcioando, toda vez que a rotina verifica ele
            // incrementa o lote
        });
    }

    @Override
    @Transactional
    public void cancelPedido(String username, Long id) {
        Pedido pedido = pedidoRepository.findById(id);
        Cliente cliente = clienteService.findByUsuario(username);
        if (pedido == null || pedido.getCliente() != cliente) {
            throw new EntidadeNotFoundException("id", "Pedido não encontrado");
        }
        Situacao situacaoAtual = pedido.getStatusPedidos().getLast().getSituacao();
        if (situacaoAtual == Situacao.ENVIADO) {
            throw new ValidationException("id", "O pedido já está com a transportadora, não é possivel cancelar");
        }

        updateStatusPedido(id, Situacao.CANCELADO);
        devolverLote(pedido);
    }

    private void devolverLote(Pedido pedido) {
        for (ItemPedido item : pedido.getItensPedidos()) {
            Lote lote = item.getLote();
            lote.setQuantidade(lote.getQuantidade() + item.getQuantidade());
        }
    }

    @Override
    public Pedido detailsPedido(Long id, String username) {
        Cliente cliente = clienteService.findByUsuario(username);
        Pedido pedido = findById(id);

        if (pedido.getCliente() != cliente) {
            throw new EntidadeNotFoundException("id", "Pedido não encontrado");
        }

        return pedido;
    }

    @Override
    public List<Pedido> eligbleReviews(Long idCliente, Long idWhey) {
        return pedidoRepository.findEligibleReviews(idCliente, idWhey);
    }

}

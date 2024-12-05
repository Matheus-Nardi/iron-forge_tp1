package br.unitins.tp1.ironforge.service.pedido;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.unitins.tp1.ironforge.dto.itempedido.ItemPedidoRequestDTO;
import br.unitins.tp1.ironforge.dto.pedido.PedidoRequestDTO;
import br.unitins.tp1.ironforge.model.endereco.Endereco;
import br.unitins.tp1.ironforge.model.pedido.Cupom;
import br.unitins.tp1.ironforge.model.pedido.EnderecoEntrega;
import br.unitins.tp1.ironforge.model.pedido.ItemPedido;
import br.unitins.tp1.ironforge.model.pedido.Lote;
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
        if (!(dto.valorTotal().equals(valorFinal)))
            throw new ValidationException("valorTotal", "O valor fornecido não corresponde ao valor final do pedido!");

        pedido.setValorTotal(valorFinal);
        pedido.setPagamento(null);
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
            int qtdeRestante = itemDTO.quantidade();
            double precoTotalItem = 0.0; 
    
            while (qtdeRestante > 0) {
                Lote lote = loteService.findByWhey(itemDTO.idProduto());
                if (lote == null || lote.getQuantidade() <= 0) {
                    throw new ValidationException("estoque", "Estoque insuficiente para o produto com ID " + itemDTO.idProduto());
                }
                int qtdeConsumida = Math.min(lote.getQuantidade(), qtdeRestante);
                double precoLote = qtdeConsumida * lote.getWheyProtein().getPreco();
                lote.setQuantidade(lote.getQuantidade() - qtdeConsumida);
                
                precoTotalItem += precoLote;
                qtdeRestante -= qtdeConsumida;
            }

            ItemPedido item = new ItemPedido();
            item.setQuantidade(itemDTO.quantidade());
            item.setPreco(precoTotalItem);
            pedido.getItensPedidos().add(item);
        }
    }

    private void obterCupom(PedidoRequestDTO dto, Pedido pedido) {
        if (dto.cupom() == null || dto.cupom().isBlank()) {
            pedido.setCupom(null);
            return;
        } else {
            List<Cupom> cupons = cupomService.findByCodigo(dto.cupom());
            System.out.println(cupons.get(0).getCodigo());
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
            total += itemPedido.getPreco();
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

        pedidos.stream()
                .filter(pedido -> pedido.getStatusPedidos()
                        .stream()
                        .anyMatch(status -> status.getSituacao() == Situacao.AGURARDANDO_PAGAMENTO &&
                                status.getDataAtualizacao().plusHours(3L).plusMinutes(30L)
                                        .isBefore(LocalDateTime.now())))
                .forEach(pedido -> {
                    updateStatusPedido(pedido.getId(), Situacao.PAGAMENTO_EXPIRADO);
                    devolverLote(pedido);
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

        if (pedido.getStatusPedidos().stream().anyMatch(e -> e.getSituacao().equals(Situacao.ENVIADO)))
            throw new ValidationException("id", "Nao é possivel cancelar, pois o pedido ja foi enviado");

        updateStatusPedido(id, Situacao.CANCELADO);
        devolverLote(pedido);
    }

    private void devolverLote(Pedido pedido) {
        for (ItemPedido item : pedido.getItensPedidos()) {
            Lote lote = item.getLote();
            Integer estoque = lote.getQuantidade();
            lote.setQuantidade(estoque + item.getQuantidade());
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

    @Override
    @Transactional
    public void returnPedido(String username, Long id) {
        Pedido pedido = findById(id);
        Cliente cliente = clienteService.findByUsuario(username);
        if (pedido.getCliente() != cliente) {
            throw new EntidadeNotFoundException("id", "Pedido não encontrado");
        }

        if (!(pedido.getStatusPedidos().stream().anyMatch(e -> e.getSituacao().equals(Situacao.ENTREGUE))))
            throw new ValidationException("id", "Nao é possivel devolver, pois o pedido ainda não foi entregue");

        updateStatusPedido(id, Situacao.DEVOLVIDO);
        devolverLote(pedido);
    }

}

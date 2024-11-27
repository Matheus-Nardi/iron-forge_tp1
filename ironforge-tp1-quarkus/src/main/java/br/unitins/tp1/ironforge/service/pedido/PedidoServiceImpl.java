package br.unitins.tp1.ironforge.service.pedido;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

}

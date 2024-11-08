package br.unitins.tp1.ironforge.service.pedido;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.unitins.tp1.ironforge.dto.itempedido.ItemPedidoRequestDTO;
import br.unitins.tp1.ironforge.dto.pedido.PedidoRequestDTO;
import br.unitins.tp1.ironforge.model.ItemPedido;
import br.unitins.tp1.ironforge.model.Lote;
import br.unitins.tp1.ironforge.model.pedido.Pedido;
import br.unitins.tp1.ironforge.repository.PedidoRepository;
import br.unitins.tp1.ironforge.service.lote.LoteService;
import br.unitins.tp1.ironforge.service.usuario.UsuarioService;
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
    public LoteService loteService;

    @Override
    public Pedido findById(Long id) {
        return pedidoRepository.findById(id);
    }

    @Override
    @Transactional
    public Pedido create(PedidoRequestDTO dto, String username) {
        Pedido pedido = new Pedido();
        pedido.setData(LocalDateTime.now());
        pedido.setUsuario(usuarioService.findByUsername(username));
        pedido.setValorTotal(dto.valorTotal());

        pedido.setItensPedidos(new ArrayList<>());
        for (ItemPedidoRequestDTO itemDTO : dto.itensPedidos()) {
            ItemPedido item = new ItemPedido();
            Lote lote = loteService.findByWhey(itemDTO.idProduto());
            item.setLote(lote);
            item.setPreco(itemDTO.preco());
            item.setQuantidade(itemDTO.quantidade());

            lote.setQuantidade(lote.getQuantidade() - itemDTO.quantidade());

            pedido.getItensPedidos().add(item);
        }

        pedidoRepository.persist(pedido);

        return pedido;
    }

    private Double calcularTotal(List<ItemPedido> itensPedidos) {
        Double total = 0.0;
        for (ItemPedido itemPedido : itensPedidos) {
            total += itemPedido.getPreco();
        }

        return total;
    }

    @Override
    public List<Pedido> findByUsername(String username) {
        return null;
    }

}

package br.unitins.tp1.ironforge.dto.pedido;

import java.time.LocalDateTime;
import java.util.List;

import br.unitins.tp1.ironforge.dto.itempedido.ItemPedidoResponseDTO;
import br.unitins.tp1.ironforge.model.pedido.Pedido;

public record PedidoResponseDTO(
                Long id,
                LocalDateTime data,
                Double valorTotal,
                List<ItemPedidoResponseDTO> itensPedidos)

{
        public static PedidoResponseDTO valueOf(Pedido pedido) {
                return new PedidoResponseDTO(pedido.getId(), pedido.getData(), pedido.getValorTotal(),
                                pedido.getItensPedidos().stream().map(ItemPedidoResponseDTO::valueOf).toList());
        }
}

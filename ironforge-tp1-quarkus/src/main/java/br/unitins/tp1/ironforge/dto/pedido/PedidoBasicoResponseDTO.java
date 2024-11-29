package br.unitins.tp1.ironforge.dto.pedido;

import java.time.LocalDateTime;
import java.util.List;

import br.unitins.tp1.ironforge.dto.itempedido.ItemPedidoResponseDTO;
import br.unitins.tp1.ironforge.model.pedido.Pedido;
import br.unitins.tp1.ironforge.model.pedido.Situacao;

public record PedidoBasicoResponseDTO(
                Long id,
                LocalDateTime data,
                Double valorTotal,
                List<ItemPedidoResponseDTO> itensPedidos,
                Situacao situacao) {

        public static PedidoBasicoResponseDTO valueOf(Pedido pedido) {
                return new PedidoBasicoResponseDTO(pedido.getId(), pedido.getData(), pedido.getValorTotal(),
                                pedido.getItensPedidos().stream().map(ItemPedidoResponseDTO::valueOf).toList(),
                                pedido.getStatusPedidos().getLast().getSituacao());
        }
}

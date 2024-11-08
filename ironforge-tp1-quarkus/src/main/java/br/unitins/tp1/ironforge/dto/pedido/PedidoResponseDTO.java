package br.unitins.tp1.ironforge.dto.pedido;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import br.unitins.tp1.ironforge.dto.itempedido.ItemPedidoResponseDTO;
import br.unitins.tp1.ironforge.model.Cupom;
import br.unitins.tp1.ironforge.model.pedido.Pedido;

public record PedidoResponseDTO(
                Long id,
                LocalDateTime data,
                Double valorTotal,
                Optional<String> cupom,
                List<ItemPedidoResponseDTO> itensPedidos)

{
        public static PedidoResponseDTO valueOf(Pedido pedido) {
                return new PedidoResponseDTO(pedido.getId(), pedido.getData(), pedido.getValorTotal(),
                                Optional.ofNullable(pedido.getCupom()).map(Cupom::getCodigo),
                                pedido.getItensPedidos().stream().map(ItemPedidoResponseDTO::valueOf).toList());
        }
}

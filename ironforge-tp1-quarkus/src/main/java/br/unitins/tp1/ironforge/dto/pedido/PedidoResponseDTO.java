package br.unitins.tp1.ironforge.dto.pedido;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import br.unitins.tp1.ironforge.dto.endereco.EnderecoResponseDTO;
import br.unitins.tp1.ironforge.dto.itempedido.ItemPedidoResponseDTO;
import br.unitins.tp1.ironforge.model.Cupom;
import br.unitins.tp1.ironforge.model.pedido.Pedido;
import br.unitins.tp1.ironforge.model.pedido.StatusPedido;

public record PedidoResponseDTO(
                Long id,
                LocalDateTime data,
                Double valorTotal,
                Optional<String> cupom,
                List<ItemPedidoResponseDTO> itensPedidos,
                List<StatusPedido> statusPedido,
                EnderecoResponseDTO enderecoEntrega)

{
        public static PedidoResponseDTO valueOf(Pedido pedido) {
                return new PedidoResponseDTO(pedido.getId(), pedido.getData(), pedido.getValorTotal(),
                                Optional.ofNullable(pedido.getCupom()).map(Cupom::getCodigo),
                                pedido.getItensPedidos().stream().map(ItemPedidoResponseDTO::valueOf).toList(),
                                pedido.getStatusPedidos(),
                                EnderecoResponseDTO.valueOf(pedido.getEnderecoEntrega()));
        }
}

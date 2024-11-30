package br.unitins.tp1.ironforge.dto.pedido;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import br.unitins.tp1.ironforge.dto.cupom.CupomResponseDTO;
import br.unitins.tp1.ironforge.dto.itempedido.ItemPedidoResponseDTO;
import br.unitins.tp1.ironforge.dto.pagamento.PagamentoResponseDTO;
import br.unitins.tp1.ironforge.model.pedido.Pedido;
import br.unitins.tp1.ironforge.model.pedido.StatusPedido;

public record PedidoDetalhadoResponseDTO(
                Long id,
                LocalDateTime data,
                Double valorTotal,
                List<ItemPedidoResponseDTO> itensPedidos,
                List<StatusPedido> statusPedidos,
                Optional<CupomResponseDTO> cupom,
                Object pagamento) {

       public static PedidoDetalhadoResponseDTO valueOf(Pedido pedido){
                return new PedidoDetalhadoResponseDTO(pedido.getId(),
                 pedido.getData(),
                 pedido.getValorTotal(),
                 pedido.getItensPedidos().stream().map(ItemPedidoResponseDTO::valueOf).toList(),
                 pedido.getStatusPedidos(),
                 Optional.ofNullable(pedido.getCupom()).map(CupomResponseDTO::valueOf),
                      PagamentoResponseDTO.valueOf(pedido.getPagamento()));
       }
}

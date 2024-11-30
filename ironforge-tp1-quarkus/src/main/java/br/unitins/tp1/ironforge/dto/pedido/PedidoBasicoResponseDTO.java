package br.unitins.tp1.ironforge.dto.pedido;

import java.time.LocalDateTime;

import br.unitins.tp1.ironforge.model.pagamento.TipoPagamento;
import br.unitins.tp1.ironforge.model.pedido.Pedido;
import br.unitins.tp1.ironforge.model.pedido.Situacao;

public record PedidoBasicoResponseDTO(
                Long id,
                LocalDateTime data,
                Double valorTotal,
                Situacao situacao,
                TipoPagamento tipoPagamento) {

        public static PedidoBasicoResponseDTO valueOf(Pedido pedido) {
                return new PedidoBasicoResponseDTO(
                                pedido.getId(),
                                pedido.getData(),
                                pedido.getValorTotal(),
                                pedido.getStatusPedidos().getLast().getSituacao(),
                                pedido.getPagamento() != null ? pedido.getPagamento().getTipoPagamento()
                                                : TipoPagamento.NAO_REALIZADO);
        }
}

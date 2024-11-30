package br.unitins.tp1.ironforge.dto.pagamento;

import br.unitins.tp1.ironforge.model.pagamento.Boleto;
import br.unitins.tp1.ironforge.model.pagamento.CartaoPagamento;
import br.unitins.tp1.ironforge.model.pagamento.Pagamento;
import br.unitins.tp1.ironforge.model.pagamento.Pix;
import br.unitins.tp1.ironforge.model.pagamento.TipoPagamento;

public record PagamentoResponseDTO(Object Pagamento) {

    public static Object valueOf(Pagamento pagamento) {
        if (pagamento instanceof Pix) {
            Pix pix = (Pix) pagamento;
            return PixResponseDTO.valueOf(pix);
        }

        if (pagamento instanceof Boleto) {
            Boleto boleto = (Boleto) pagamento;
            return BoletoResponseDTO.valueOf(boleto);
        }

        if (pagamento instanceof CartaoPagamento) {
            CartaoPagamento cartao = (CartaoPagamento) pagamento;
            return CartaoPagamentoResponseDTO.valueOf(cartao);
        }

        return TipoPagamento.NAO_REALIZADO;
    }
}

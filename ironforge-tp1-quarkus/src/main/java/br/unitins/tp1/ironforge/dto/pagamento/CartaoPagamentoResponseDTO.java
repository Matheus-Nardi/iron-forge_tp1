package br.unitins.tp1.ironforge.dto.pagamento;

import java.time.LocalDate;

import br.unitins.tp1.ironforge.model.pagamento.CartaoPagamento;
import br.unitins.tp1.ironforge.model.pagamento.TipoCartao;
import br.unitins.tp1.ironforge.model.pagamento.TipoPagamento;

public record CartaoPagamentoResponseDTO(
        Long id,
        String titular,
        String numero,
        LocalDate validade,
        TipoCartao tipoCartao,
        TipoPagamento tipoPagamento,
        String pago) {

    public static CartaoPagamentoResponseDTO valueOf(CartaoPagamento cartao) {
        return new CartaoPagamentoResponseDTO(cartao.getId(), cartao.getTitular(), mascararNumeroCartao(cartao.getNumero()),
                cartao.getValidade(),
                cartao.getTipoCartao(),
                cartao.getTipoPagamento(),
                cartao.getPago() == true ? "Sim" : "Não");
    }

    private static String mascararNumeroCartao(String numero) {
        if (numero == null || numero.length() < 4) {
            return "****";
        }
        int length = numero.length();

        StringBuilder mascarado = new StringBuilder();
        for (int i = 0; i < length - 4; i++) {
            if (i > 0 && i % 4 == 0) {
                mascarado.append(" ");
            }
            mascarado.append("*");
        }

        mascarado.append(" ").append(numero.substring(length - 4));
        return mascarado.toString().trim();
    }

}

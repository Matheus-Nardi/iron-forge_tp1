package br.unitins.tp1.ironforge.dto.pagamento;

import java.time.LocalDate;

import br.unitins.tp1.ironforge.model.pagamento.Cartao;
import br.unitins.tp1.ironforge.model.pagamento.TipoCartao;

public record CartaoResponseDTO(
        Long id,
        String titular,
        String numero,
        LocalDate validade,
        TipoCartao tipoCartao) {

    public static CartaoResponseDTO valueOf(Cartao cartao) {
        return new CartaoResponseDTO(cartao.getId(), cartao.getTitular(), mascararNumeroCartao(cartao.getNumero()),
                cartao.getValidade(),
                cartao.getTipoCartao());
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

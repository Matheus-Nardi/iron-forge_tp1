package br.unitins.tp1.ironforge.dto.pagamento;

import java.time.LocalDateTime;

import br.unitins.tp1.ironforge.model.pagamento.Boleto;
import br.unitins.tp1.ironforge.model.pagamento.TipoPagamento;

public record BoletoResponseDTO(
        Long id,
        String codigo,
        LocalDateTime dataVencimento,
        Double valor,
        TipoPagamento tipoPagamento,
        String pago) {

    public static BoletoResponseDTO valueOf(Boleto boleto) {
        return new BoletoResponseDTO(boleto.getId(), boleto.getCodigoBarras(), boleto.getDataVencimento(),
                boleto.getValor(),
                boleto.getTipoPagamento(),
                boleto.getPago() == true ? "Sim" : "Não");
    }
}

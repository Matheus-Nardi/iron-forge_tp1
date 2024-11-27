package br.unitins.tp1.ironforge.dto.pagamento;

import java.time.LocalDateTime;

import br.unitins.tp1.ironforge.model.pagamento.Boleto;

public record BoletoResponseDTO(
        Long id,
        String codigo,
        LocalDateTime dataVencimento,
        Double valor) {

    public static BoletoResponseDTO valueOf(Boleto boleto) {
        return new BoletoResponseDTO(boleto.getId(), boleto.getCodigoBarras(), boleto.getDataVencimento(),
                boleto.getValor());
    }
}

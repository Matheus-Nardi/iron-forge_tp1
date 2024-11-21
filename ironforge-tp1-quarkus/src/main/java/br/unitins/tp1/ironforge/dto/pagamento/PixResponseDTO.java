package br.unitins.tp1.ironforge.dto.pagamento;

import java.time.LocalDateTime;

import br.unitins.tp1.ironforge.model.pagamento.Pix;

public record PixResponseDTO(
        Long id,
        String chave,
        String destinatario,
        LocalDateTime dataVencimento,
        Double valor) {

    public static PixResponseDTO valueOf(Pix pix) {
        return new PixResponseDTO(pix.getId(), pix.getChave(), pix.getDestinatario(), pix.getDataVencimento(),
                pix.getValor());
    }
}

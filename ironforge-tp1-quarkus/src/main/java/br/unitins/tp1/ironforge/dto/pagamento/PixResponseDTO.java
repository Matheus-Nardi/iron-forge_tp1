package br.unitins.tp1.ironforge.dto.pagamento;

import java.time.LocalDateTime;

import br.unitins.tp1.ironforge.model.pagamento.Pix;
import br.unitins.tp1.ironforge.model.pagamento.TipoPagamento;

public record PixResponseDTO(
        Long id,
        String chave,
        String destinatario,
        LocalDateTime dataVencimento,
        Double valor,
        TipoPagamento tipoPagamento,
        String pago) {

    public static PixResponseDTO valueOf(Pix pix) {
        return new PixResponseDTO(pix.getId(), pix.getChave(), pix.getDestinatario(), pix.getDataVencimento(),
                pix.getValor(),
                pix.getTipoPagamento(),
                pix.getPago() == true ? "Sim" : "Não");
    }
}

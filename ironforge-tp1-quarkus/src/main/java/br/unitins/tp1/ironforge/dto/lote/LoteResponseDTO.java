package br.unitins.tp1.ironforge.dto.lote;

import java.time.LocalDate;

import br.unitins.tp1.ironforge.model.pedido.Lote;

public record LoteResponseDTO(
        Long id,
        String codigo,
        Integer quantidade,
        LocalDate dataFabricacao,
        String wheyProtein) {
    public static LoteResponseDTO valueOf(Lote lote) {
        return new LoteResponseDTO(lote.getId(), lote.getCodigo(), lote.getQuantidade(), lote.getDataFabricacao(),
                lote.getWheyProtein().getNome());
    }
}

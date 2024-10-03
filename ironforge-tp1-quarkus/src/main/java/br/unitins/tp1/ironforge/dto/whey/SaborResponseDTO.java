package br.unitins.tp1.ironforge.dto.whey;

import br.unitins.tp1.ironforge.model.whey.Sabor;

public record SaborResponseDTO(
        Long id,
        String nome) {
    public static SaborResponseDTO valueOf(Sabor sabor) {
        return new SaborResponseDTO(sabor.getId(), sabor.getNome());
    }
}

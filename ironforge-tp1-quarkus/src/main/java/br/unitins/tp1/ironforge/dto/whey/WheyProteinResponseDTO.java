package br.unitins.tp1.ironforge.dto.whey;

import br.unitins.tp1.ironforge.model.WheyProtein;

public record WheyProteinResponseDTO(
        Long id,
        String nome,
        String descricao,
        Double preco,
        Integer peso) {

    public static WheyProteinResponseDTO valueOf(WheyProtein whey) {
        return new WheyProteinResponseDTO(whey.getId(),
                whey.getNome(),
                whey.getDescricao(),
                whey.getPreco(),
                whey.getPeso());
    }
}

package br.unitins.tp1.ironforge.dto.whey;

import br.unitins.tp1.ironforge.dto.fabricante.FabricanteResponseDTO;
import br.unitins.tp1.ironforge.model.whey.TipoWhey;
import br.unitins.tp1.ironforge.model.whey.WheyProtein;

public record WheyProteinResponseDTO(
        Long id,
        String nome,
        String descricao,
        SaborResponseDTO sabor,
        TipoWhey tipoWhey,
        Double preco,
        Integer peso,
        FabricanteResponseDTO fabricante) {

    public static WheyProteinResponseDTO valueOf(WheyProtein whey) {
        return new WheyProteinResponseDTO(whey.getId(),
                whey.getNome(),
                whey.getDescricao(),
                SaborResponseDTO.valueOf(whey.getSabor()),
                whey.getTipoWhey(),
                whey.getPreco(),
                whey.getPeso(),
                FabricanteResponseDTO.valueOf(whey.getFabricante()));
    }
}

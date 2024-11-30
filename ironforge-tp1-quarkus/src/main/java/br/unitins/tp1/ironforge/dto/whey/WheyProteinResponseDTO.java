package br.unitins.tp1.ironforge.dto.whey;

import br.unitins.tp1.ironforge.model.whey.TipoWhey;
import br.unitins.tp1.ironforge.model.whey.WheyProtein;

public record WheyProteinResponseDTO(
        Long id,
        String nome,
        String descricao,
        Double nota,
        SaborResponseDTO sabor,
        TipoWhey tipoWhey,
        Double preco,
        Integer peso,
        String fabricante,
        TabelaNutricionalResponseDTO tabelaNutricional) {

    public static WheyProteinResponseDTO valueOf(WheyProtein whey) {
        return new WheyProteinResponseDTO(whey.getId(),
                whey.getNome(),
                whey.getDescricao(),
                whey.getNota(),
                SaborResponseDTO.valueOf(whey.getSabor()),
                whey.getTipoWhey(),
                whey.getPreco(),
                whey.getPeso(),
                whey.getFabricante().getPessoaJuridica().getNome(),
                TabelaNutricionalResponseDTO.valeuOf(whey.getFood()));
    }
}

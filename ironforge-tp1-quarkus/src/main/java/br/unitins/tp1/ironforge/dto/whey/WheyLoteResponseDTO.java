package br.unitins.tp1.ironforge.dto.whey;

import java.util.List;

import br.unitins.tp1.ironforge.model.whey.TipoWhey;
import br.unitins.tp1.ironforge.model.whey.WheyProtein;

public record WheyLoteResponseDTO(
        Long id,
        String nome,
        SaborResponseDTO sabor,
        TipoWhey tipo,
        Double preco,
        Integer peso,
        String fabricante,
        List<String> imagens) {
    public static WheyLoteResponseDTO valueOf(WheyProtein whey) {
        return new WheyLoteResponseDTO(whey.getId(), whey.getNome(), SaborResponseDTO.valueOf(whey.getSabor()),
                whey.getTipoWhey(), whey.getPreco(),
                whey.getPeso(), whey.getFabricante().getPessoaJuridica().getNome(), whey.getImagens());

    }
}

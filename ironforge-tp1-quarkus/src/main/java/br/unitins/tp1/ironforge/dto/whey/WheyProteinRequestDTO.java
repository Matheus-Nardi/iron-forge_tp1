package br.unitins.tp1.ironforge.dto.whey;

public record WheyProteinRequestDTO(
        String nome,
        String descricao,
        Long idSabor,
        Integer idTipo,
        Double preco,
        Integer peso,
        Long idFabricante) {

}

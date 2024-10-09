package br.unitins.tp1.ironforge.dto.whey;

public record WheyProteinRequestDTO(
        String upc,
        String nome,
        String descricao,
        Long idSabor,
        int idTipo,
        Double preco,
        Integer peso,
        Long idFabricante) {

}

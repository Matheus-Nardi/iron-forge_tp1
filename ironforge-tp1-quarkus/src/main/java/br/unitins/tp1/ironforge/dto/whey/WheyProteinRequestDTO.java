package br.unitins.tp1.ironforge.dto.whey;

public record WheyProteinRequestDTO(
        String nome,
        String descricao,
        Double preco,
        Integer peso) {

}

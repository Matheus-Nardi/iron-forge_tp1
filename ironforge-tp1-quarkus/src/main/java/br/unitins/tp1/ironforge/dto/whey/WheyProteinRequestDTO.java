package br.unitins.tp1.ironforge.dto.whey;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record WheyProteinRequestDTO(
                @NotBlank(message = "O campo código de barras deve ser informado") String upc,
                @NotBlank(message = "O campo nome deve ser informado") String nome,
                String descricao,
                @NotNull(message = "O campo sabor deve ser informado") Long idSabor,
                @NotNull(message = "O campo tipo deve ser informado") Integer idTipo,
                @NotNull(message = "O campo preço deve ser informado") Double preco,
                @NotNull(message = "O campo peso deve ser informado") Integer peso,
                @NotNull(message = "O campo fabricante deve ser informado") Long idFabricante) {

}

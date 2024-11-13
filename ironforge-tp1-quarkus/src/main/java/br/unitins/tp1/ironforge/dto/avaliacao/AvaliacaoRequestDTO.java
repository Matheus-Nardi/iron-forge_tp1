package br.unitins.tp1.ironforge.dto.avaliacao;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AvaliacaoRequestDTO(
        @NotNull(message = "O campo whey deve ser informado") Long idWhey,
        @NotBlank(message = "O campo comentario deve ser informado") String comentario,
        @NotNull(message = "O campo nota deve ser informado") @Min(value = 1, message = "A nota deve ser no mínimo 1") @Max(value = 5, message = "A nota deve ser no máximo 5") Integer nota) {

}

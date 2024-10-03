package br.unitins.tp1.ironforge.dto.whey;

import jakarta.validation.constraints.NotBlank;

public record SaborRequestDTO(
        @NotBlank(message = "O campo nome deve ser informado.")
        String nome) {

}

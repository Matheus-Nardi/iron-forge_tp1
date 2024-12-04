package br.unitins.tp1.ironforge.dto.estado;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EstadoRequestDTO(
        @NotBlank(message = "Nome não pode ser vazio !") @Size(max = 60, message = "O nome deve possuir no máximo 60 caracteres") String nome,
        @NotBlank(message = "Sigla não pode ser vazia !") @Size(min = 2, max = 2, message = "A sigla deve ter somente 2 caracteres") String sigla) {

}

package br.unitins.tp1.ironforge.dto.cidade;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CidadeRequestDTO(
        @NotEmpty(message = "O campo nome deve ser informado.") @Size(max = 60, message = "O campo nome deve conter no máximo 60 caracteres.") String nome,
        @NotNull(message = "O campo idEstado não pode ser nulo.") Long idEstado) {

}

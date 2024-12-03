package br.unitins.tp1.ironforge.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailPatchDTO(
        @NotBlank(message = "O email não pode ser nulo ou vazio") @Email(message = "O email está em formato inválido!") String email) {

}

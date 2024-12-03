package br.unitins.tp1.ironforge.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioRequestDTO(
                @NotBlank(message = "O campo nome de usuario deve ser informado") String username,
                @NotBlank(message = "O campo senha deve sr informado") @Size(min = 5, max = 20, message = "A senha deve ter entre 5 e 20 caracteres") String senha,
                @NotBlank(message = "O email deve ser informado") @Email(message = "O email deve ser válido") String email) {

}

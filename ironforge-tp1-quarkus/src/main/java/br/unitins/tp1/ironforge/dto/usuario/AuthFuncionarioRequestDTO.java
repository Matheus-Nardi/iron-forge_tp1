package br.unitins.tp1.ironforge.dto.usuario;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AuthFuncionarioRequestDTO(

                @NotBlank(message = "O campo nome de usuario deve ser informado") String username,
                @NotBlank(message = "O campo senha deve sr informado") @Size(min = 5, max = 20, message = "A senha deve ter entre 5 e 20 caracteres") String senha,
                @NotNull(message = "O perfil deve ser informado") @Min(value = 1, message = "O perfil informado deve ser no mínimo 1(Funcionario)") @Max(value = 2, message = "O perfil informado deve ser no máximo 2(Cliente)") Integer perfil) {

}

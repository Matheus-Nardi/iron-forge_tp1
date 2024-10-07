package br.unitins.tp1.ironforge.dto.usuario;

import jakarta.validation.constraints.NotBlank;

public record TelefoneRequestDTO(
        @NotBlank(message = "O campo codigo de área deve ser informado") String codigoArea,
        @NotBlank(message = "O campo codigo de área deve ser informado") String numero

) {

}

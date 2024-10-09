package br.unitins.tp1.ironforge.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TelefoneRequestDTO(
        @NotBlank(message = "O campo codigo de área deve ser informado") 
        @Size(min = 2 , max = 2 , message = "O código de área conter 2 digitos")
        String codigoArea,
        @NotBlank(message = "O campo codigo de área deve ser informado")
        @Size(min = 9 , max = 9, message = "O campo número deve conter 11 digitos ")
        String numero

) {

}

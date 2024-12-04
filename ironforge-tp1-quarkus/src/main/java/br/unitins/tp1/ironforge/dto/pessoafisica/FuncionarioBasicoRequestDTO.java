package br.unitins.tp1.ironforge.dto.pessoafisica;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record FuncionarioBasicoRequestDTO(

        @NotNull(message = "O campo salário deve ser informado") BigDecimal salario,
        @NotEmpty(message = "O campo data de contracacao deve ser informado")
        LocalDate dataContratacao,
        @NotEmpty(message = "O campo cargo deve ser informado")
        String cargo) {

}

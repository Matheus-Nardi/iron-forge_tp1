package br.unitins.tp1.ironforge.dto.pessoafisica;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record FuncionarioBasicoRequestDTO(

        @NotNull(message = "O campo salário deve ser informado") BigDecimal salario,
        LocalDate dataContratacao,
        String cargo) {

}

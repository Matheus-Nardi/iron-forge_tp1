package br.unitins.tp1.ironforge.dto.cupom;

import java.time.LocalDateTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CupomRequestDTO(
    @NotNull(message = "O campo fabricante deve ser informado")
    Long idFabricante,
    @NotBlank(message = "O campo código deve ser informado")
    @Size(min = 6, max = 12, message = "O código deve possuir entre 6 e 12 caracteres")
    String codigo,
    @NotNull(message = "O campo percentual de desconto deve ser informado")
    Double percentualDesconto,
    @NotNull(message = "O campo data de validade deve ser informado")
    @FutureOrPresent(message = "A data de validade deve ser hoje ou uma data futura")
    LocalDateTime dataValidade,
    Boolean ativo
) {

}

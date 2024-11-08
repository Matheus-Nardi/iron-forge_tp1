package br.unitins.tp1.ironforge.dto.lote;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public record LoteRequestDTO(
        @NotBlank(message = "O campo código deve ser informado")
        String codigo,
        @NotNull(message = "O campo quantidade deve ser informado") Integer quantidade,
        @NotNull(message = "A data não pode ser nula") @PastOrPresent(message = "A data informada deve ser hoje ou estar no passado") LocalDate dataFabricacao , 
        @NotNull(message = "O campo id whey deve ser informado.")
        Long idWhey) {

}

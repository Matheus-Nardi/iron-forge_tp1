package br.unitins.tp1.ironforge.dto.pagamento;

import java.time.LocalDate;

import br.unitins.tp1.ironforge.model.pagamento.TipoCartao;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CartaoRequestDTO(
        @NotBlank(message = "O cpf deve ser informado") String cpf,

        @NotBlank(message = "O nome do titular deve ser informado") String titular,

        @NotBlank(message = "O número do cartão deve ser informado") @Size(min = 14, max = 19, message = "O número do cartão deve conter entre 14 e 19 dígitos") String numero,

        @NotBlank(message = "O código de verificação do cartão deve ser informado") @Size(min = 3, max = 4, message = "O código de verificação deve conter entre 3 e 4 dígitos") String cvc,

        @NotNull(message = "A validade do cartão deve ser informada") @Future(message = "A validade do cartão deve ser uma data futura") LocalDate validade,

        @NotNull(message = "O tipo do cartão deve ser informado") TipoCartao tipoCartao) {
}

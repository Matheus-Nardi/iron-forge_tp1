package br.unitins.tp1.ironforge.dto.pessoafisica;

import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

public record ClienteUpdateRequestDTO(
        @NotBlank(message = "O campo nome deve ser informado.") String nome,
        @NotBlank(message = "O campo CPF deve ser informado.") @CPF(message = "O CPF informado está em formato inválido") String cpf,
        @Past(message = "A data informada deve estar no passado")
        LocalDate dataNascimento) {

}

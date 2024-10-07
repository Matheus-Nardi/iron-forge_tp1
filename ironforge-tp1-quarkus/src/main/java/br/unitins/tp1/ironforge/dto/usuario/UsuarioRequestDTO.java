package br.unitins.tp1.ironforge.dto.usuario;

import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioRequestDTO(
                @NotBlank(message = "O campo nome deve ser informado") String nome,

                @NotBlank(message = "O campo CPF deve ser informado") @CPF(message = "O CPF deve ser válido") String cpf,

                @NotBlank(message = "O campo email deve ser informado") @Email(message = "O email deve ser válido") String email,

                @NotBlank(message = "O campo senha deve ser informado") @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres") String senha,
                @JsonFormat(pattern = "dd/MM/yyyy") LocalDate dataNascimento,

                @NotBlank(message = "O campo telefone deve ser informado") TelefoneRequestDTO telefone)

{

}

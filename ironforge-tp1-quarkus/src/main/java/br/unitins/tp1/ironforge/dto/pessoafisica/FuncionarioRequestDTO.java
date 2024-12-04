package br.unitins.tp1.ironforge.dto.pessoafisica;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.hibernate.validator.constraints.br.CPF;

import br.unitins.tp1.ironforge.dto.endereco.EnderecoRequestDTO;
import br.unitins.tp1.ironforge.dto.telefone.TelefoneRequestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record FuncionarioRequestDTO(

        @NotBlank(message = "O campo nome deve ser informado.") String nome,
        @NotBlank(message = "O campo CPF deve ser informado.") @CPF(message = "O CPF informado está em formato inválido") String cpf,
        LocalDate dataNascimento,
        List<TelefoneRequestDTO> telefones,
        List<EnderecoRequestDTO> enderecos,
        @NotNull(message = "O campo salário deve ser informado") BigDecimal salario,
        @NotEmpty(message = "O campo data de contracacao deve ser informado") LocalDate dataContratacao,
        @NotEmpty(message = "O campo cargo deve ser informado") String cargo) {

}

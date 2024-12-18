package br.unitins.tp1.ironforge.dto.pessoafisica;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.validator.constraints.br.CPF;

import br.unitins.tp1.ironforge.dto.endereco.EnderecoRequestDTO;
import br.unitins.tp1.ironforge.dto.telefone.TelefoneRequestDTO;
import jakarta.validation.constraints.NotBlank;

public record ClienteRequestDTO(

                @NotBlank(message = "O campo nome deve ser informado.") String nome,
                @NotBlank(message = "O campo CPF deve ser informado.") @CPF(message = "O CPF informado está em formato inválido") String cpf,
                LocalDate dataNascimento,
                List<TelefoneRequestDTO> telefones,
                List<EnderecoRequestDTO> enderecos) {

}

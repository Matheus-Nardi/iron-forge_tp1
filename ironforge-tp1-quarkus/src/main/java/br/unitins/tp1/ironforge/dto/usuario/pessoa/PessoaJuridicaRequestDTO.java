package br.unitins.tp1.ironforge.dto.usuario.pessoa;

import org.hibernate.validator.constraints.br.CNPJ;

import jakarta.validation.constraints.NotBlank;

public record PessoaJuridicaRequestDTO(
        @NotBlank(message = "Nome não pode ser vazio !") String nome,
        @NotBlank(message = "CNPJ não pode ser vazia !")
        @CNPJ(message = "Informe um CNPJ válido")
        String cnpj) {

}

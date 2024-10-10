package br.unitins.tp1.ironforge.dto.usuario.pessoa;

import br.unitins.tp1.ironforge.model.usuario.PessoaJuridica;

public record PessoaJuridicaResponseDTO(
        Long id,
        String nome,
        String cnpj) {

    public static PessoaJuridicaResponseDTO valueOf(PessoaJuridica dto) {
        return new PessoaJuridicaResponseDTO(dto.getId(), dto.getNome(), dto.getCnpj());
    }
}

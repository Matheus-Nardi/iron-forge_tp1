package br.unitins.tp1.ironforge.dto.usuario;

import br.unitins.tp1.ironforge.dto.endereco.EnderecoResponseDTO;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        TelefoneResponseDTO telefone,
        EnderecoResponseDTO endereco) {
}

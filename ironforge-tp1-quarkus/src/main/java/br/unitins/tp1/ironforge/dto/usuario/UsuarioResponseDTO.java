package br.unitins.tp1.ironforge.dto.usuario;

import java.util.List;

import br.unitins.tp1.ironforge.dto.endereco.EnderecoResponseDTO;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        List<TelefoneResponseDTO> telefones,
        List<EnderecoResponseDTO> enderecos) {
}

package br.unitins.tp1.ironforge.dto.usuario;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        TelefoneResponseDTO telefone) {
}

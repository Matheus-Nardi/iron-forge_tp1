package br.unitins.tp1.ironforge.dto.usuario;

import java.time.LocalDate;

import br.unitins.tp1.ironforge.model.usuario.Cliente;
import br.unitins.tp1.ironforge.model.usuario.Usuario;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        LocalDate dataNascimento) {

    public static UsuarioResponseDTO valueOfCliente(Cliente cliente) {
        Usuario usuario = cliente.getUsuario();
        return new UsuarioResponseDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(),
                usuario.getDataNascimento());
    }
}

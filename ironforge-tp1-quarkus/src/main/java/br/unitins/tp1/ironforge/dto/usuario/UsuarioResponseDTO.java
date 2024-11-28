package br.unitins.tp1.ironforge.dto.usuario;

import br.unitins.tp1.ironforge.model.Perfil;
import br.unitins.tp1.ironforge.model.usuario.Usuario;

public record UsuarioResponseDTO(
                Long id,
                String username,
                Perfil perfil) {

        public static UsuarioResponseDTO valueOf(Usuario usuario) {
                return new UsuarioResponseDTO(usuario.getId(), usuario.getUsername(),
                                usuario.getPerfil());
        }
}

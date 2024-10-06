package br.unitins.tp1.ironforge.dto.usuario.cliente;

import br.unitins.tp1.ironforge.dto.usuario.UsuarioResponseDTO;
import br.unitins.tp1.ironforge.model.usuario.Cliente;

public record ClienteResponseDTO(
        UsuarioResponseDTO usuario) {

    public static ClienteResponseDTO valueOf(Cliente cliente) {
        return new ClienteResponseDTO(new UsuarioResponseDTO(cliente.getId(), cliente.getUsuario().getNome(),
                cliente.getUsuario().getEmail()));
    }
}

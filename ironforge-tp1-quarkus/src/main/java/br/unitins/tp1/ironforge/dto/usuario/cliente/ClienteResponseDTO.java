package br.unitins.tp1.ironforge.dto.usuario.cliente;

import br.unitins.tp1.ironforge.dto.endereco.EnderecoResponseDTO;
import br.unitins.tp1.ironforge.dto.usuario.TelefoneResponseDTO;
import br.unitins.tp1.ironforge.dto.usuario.UsuarioResponseDTO;
import br.unitins.tp1.ironforge.model.usuario.Cliente;

public record ClienteResponseDTO(
        Long id,
        UsuarioResponseDTO usuario) {

    public static ClienteResponseDTO valueOf(Cliente cliente) {
        return new ClienteResponseDTO(cliente.getId(),
                new UsuarioResponseDTO(cliente.getUsuario().getId(), cliente.getUsuario().getNome(),
                        cliente.getUsuario().getEmail(),
                        TelefoneResponseDTO.valueOf(cliente.getUsuario().getTelefone()) , EnderecoResponseDTO.valueOf(cliente.getUsuario().getEndereco())));
    }
}

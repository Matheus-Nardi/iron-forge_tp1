package br.unitins.tp1.ironforge.dto.usuario.cliente;

import br.unitins.tp1.ironforge.dto.usuario.UsuarioCreateRequestDTO;

public record ClienteCreateRequestDTO(
        UsuarioCreateRequestDTO usuario) {

}

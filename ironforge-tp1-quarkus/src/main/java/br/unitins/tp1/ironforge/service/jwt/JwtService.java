package br.unitins.tp1.ironforge.service.jwt;

import br.unitins.tp1.ironforge.dto.usuario.UsuarioResponseDTO;

public interface JwtService {

    String generateJwt(UsuarioResponseDTO dto);
}

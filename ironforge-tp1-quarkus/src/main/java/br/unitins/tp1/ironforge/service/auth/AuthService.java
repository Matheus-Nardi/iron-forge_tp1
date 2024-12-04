package br.unitins.tp1.ironforge.service.auth;

import br.unitins.tp1.ironforge.model.usuario.Usuario;

public interface AuthService {

    void changeRole(Usuario usuario, Integer idPerfil);

}

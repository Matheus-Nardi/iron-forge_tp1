package br.unitins.tp1.ironforge.service.auth;

import br.unitins.tp1.ironforge.model.usuario.Perfil;
import br.unitins.tp1.ironforge.model.usuario.Usuario;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AuthServiceImpl implements AuthService {

    @Override
    public void changeRole(Usuario usuario, Integer idPerfil) {
        Perfil novoPerfil = Perfil.valueOf(idPerfil);

        if (!usuario.getListaPerfil().contains(novoPerfil)) {
            usuario.getListaPerfil().clear();
            usuario.getListaPerfil().add(novoPerfil);
        }
    }

}

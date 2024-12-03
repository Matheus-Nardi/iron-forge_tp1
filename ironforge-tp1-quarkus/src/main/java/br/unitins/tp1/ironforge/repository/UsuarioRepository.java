package br.unitins.tp1.ironforge.repository;

import br.unitins.tp1.ironforge.model.usuario.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {

    public Usuario findByUsernameAndSenha(String username, String senha) {
        return find("username = ?1 AND senha = ?2", username, senha).firstResult();
    }

    public Usuario findByUsername(String username) {
        return find("username = ?1", username).firstResult();
    }

    public Usuario findByEmail(String email) {
        return find("email = ?1", email).firstResult();
    }

}

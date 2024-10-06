package br.unitins.tp1.ironforge.repository;

import java.util.List;

import br.unitins.tp1.ironforge.model.usuario.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {

    public List<Usuario> findClienteByNome(String nome) {
        return find("SELECT u FROM Usuario u JOIN Cliente c ON c.usuario.id = u.id WHERE u.nome LIKE ?1",
                "%" + nome + "%").list();
    }

    public List<Usuario> findFuncionarioByNome(String nome) {
        return find("SELECT u FROM Usuario u JOIN Funcionario f ON f.usuario.id = u.id WHERE u.nome LIKE ?1",
                "%" + nome + "%").list();
    }

    public boolean existByCpf(String cpf) {
        return find("cpf", cpf).firstResultOptional().isPresent();
    }

    public boolean existByEmail(String email) {
        return find("email", email).firstResultOptional().isPresent();
    }

}

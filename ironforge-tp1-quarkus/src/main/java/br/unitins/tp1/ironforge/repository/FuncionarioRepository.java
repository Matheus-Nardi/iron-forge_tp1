package br.unitins.tp1.ironforge.repository;

import br.unitins.tp1.ironforge.model.usuario.Funcionario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FuncionarioRepository implements PanacheRepository<Funcionario> {

    public Funcionario findFuncionarioByUsuario(Long idUsuario) {
        return find("usuario.id", idUsuario).firstResult();
    }
}

package br.unitins.tp1.ironforge.repository;

import java.util.List;

import br.unitins.tp1.ironforge.model.usuario.Funcionario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FuncionarioRepository implements PanacheRepository<Funcionario> {

    public List<Funcionario> findFuncionarioByNome(String nome) {
        return find("pessoaFisica.nome LIKE ?1", "%" + nome + "%").list();
    }
}

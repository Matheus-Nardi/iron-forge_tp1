package br.unitins.tp1.ironforge.repository;

import java.util.List;

import br.unitins.tp1.ironforge.model.Fabricante;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FabricanteRepository implements PanacheRepository<Fabricante> {

    public List<Fabricante> findByNome(String nome) {
        return find("pessoaJuridica.nome LIKE ?1", "%" + nome + "%").list();
    }

    public Fabricante findFabricanteByUsername(String username) {
        return find("pessoaJuridica.usuario.username = ?1 ", username).firstResult();
    }

    public Fabricante findFabricanteByCnpj(String cnpj) {
        return find("pessoaJuridica.cnpj = ?1 ", cnpj).firstResult();
    }

    public Fabricante findFabricanteByEmail(String email) {
        return find("pessoaJuridica.email = ?1 ", email).firstResult();
    }
}

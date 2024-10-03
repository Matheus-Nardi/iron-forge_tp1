package br.unitins.tp1.ironforge.repository;

import java.util.List;

import br.unitins.tp1.ironforge.model.Fabricante;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FabricanteRepository implements PanacheRepository<Fabricante> {

    public List<Fabricante> findByNome(String nome) {
        return find("select f from Fabricante f WHERE f.nome LIKE ?1", "%" + nome + "%").list();
    }
}

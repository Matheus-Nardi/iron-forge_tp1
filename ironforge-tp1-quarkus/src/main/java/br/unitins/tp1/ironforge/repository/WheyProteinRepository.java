package br.unitins.tp1.ironforge.repository;

import java.util.List;

import br.unitins.tp1.ironforge.model.whey.WheyProtein;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WheyProteinRepository implements PanacheRepository<WheyProtein> {

    public List<WheyProtein> findByNome(String nome) {
        return find("select w from WheyProtein w WHERE w.nome LIKE ?1", "%" + nome + "%").list();
    }

    public List<WheyProtein> findByPreco(Double preco) {
        return find("select w from WheyProtein w WHERE w.preco <= ?1 ", preco).list();
    }

}

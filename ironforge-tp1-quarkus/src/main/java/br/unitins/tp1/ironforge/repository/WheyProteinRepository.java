package br.unitins.tp1.ironforge.repository;

import java.util.List;

import br.unitins.tp1.ironforge.model.whey.TipoWhey;
import br.unitins.tp1.ironforge.model.whey.WheyProtein;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WheyProteinRepository implements PanacheRepository<WheyProtein> {

    public List<WheyProtein> findByNome(String nome) {
        return find("select w from WheyProtein w WHERE w.nome LIKE ?1", "%" + nome + "%").list();
    }

    public List<WheyProtein> findByPreco(Double preco) {
        return find("select w from WheyProtein w WHERE w.preco <= ?1 ", preco).list();
    }

    public List<WheyProtein> findByPrecoMinAndMax(Double precoMin, Double precoMax) {
        return find("preco BETWEEN ?1 AND ?2", precoMin, precoMax).list();
    }

    public List<WheyProtein> findByTipo(TipoWhey tipo) {
        return find("tipoWhey = ?1", tipo).list();
    }

    // Usando de outra forma com Parameters With
    public List<WheyProtein> findBySabor(String sabor) {
        String saborFormat = sabor.substring(0, 1).toUpperCase() + sabor.substring(1).toLowerCase();
        return find("sabor.nome LIKE :sabor", Parameters.with("sabor", "%" + saborFormat + "%")).list();
    }

}

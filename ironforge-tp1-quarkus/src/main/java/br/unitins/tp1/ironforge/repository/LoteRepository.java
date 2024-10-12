package br.unitins.tp1.ironforge.repository;

import java.util.List;

import br.unitins.tp1.ironforge.model.Lote;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LoteRepository implements PanacheRepository<Lote> {

    public List<Lote> findByCodigo(String codigo) {
        return find("codigo LIKE ?1", "%" + codigo + "%").list();
    }

    public List<Lote> findByWhey(Long id){
        return find("wheyProtein.id = ?1", id).list();
    }
}

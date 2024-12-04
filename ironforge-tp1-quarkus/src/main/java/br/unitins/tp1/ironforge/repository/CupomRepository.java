package br.unitins.tp1.ironforge.repository;

import java.util.List;

import br.unitins.tp1.ironforge.model.pedido.Cupom;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CupomRepository implements PanacheRepository<Cupom> {

    public List<Cupom> findByCodigo(String codigo) {
        return find("codigo = ?1", codigo).list();
    }

    public List<Cupom> findByFabricante(Long idFabricante) {
        return find("fabricante.id = ?1", idFabricante).list();
    }
}

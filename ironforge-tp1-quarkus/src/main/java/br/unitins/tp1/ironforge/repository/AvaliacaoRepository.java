package br.unitins.tp1.ironforge.repository;

import java.util.List;

import br.unitins.tp1.ironforge.model.avaliacao.Avaliacao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AvaliacaoRepository implements PanacheRepository<Avaliacao> {

    public List<Avaliacao> findByCliente(Long idCliente) {
        return find("cliente = ?1", idCliente).list();
    }

    public List<Avaliacao> findByWhey(Long idWhey) {
        return find("wheyProtein = ?1", idWhey).list();
    }
}

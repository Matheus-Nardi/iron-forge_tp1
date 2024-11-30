package br.unitins.tp1.ironforge.repository;

import java.util.List;

import br.unitins.tp1.ironforge.model.avaliacao.Avaliacao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AvaliacaoRepository implements PanacheRepository<Avaliacao> {

    public List<Avaliacao> findByCliente(Long idCliente) {
        return find("cliente.id = ?1", idCliente).list();
    }

    public List<Avaliacao> findByWhey(Long idWhey) {
        return find("wheyProtein.id = ?1", idWhey).list();
    }

    public Long counByWhey(Long idWhey){
        return find("wheyProtein.id = ?1", idWhey).count();
    }

    public boolean customerHasAlreadyReviewedProduct(Long idCliente, Long idProduto) {
        Long count = find("cliente.id = ?1 and wheyProtein.id = ?2", idCliente, idProduto).count();
        return count > 0;
    }
    
}

package br.unitins.tp1.ironforge.repository;

import java.util.List;

import br.unitins.tp1.ironforge.model.Estado;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EstadoRepository implements PanacheRepository<Estado> {

  
    public List<Estado> findByNome(String nome){
        return find("nome LIKE ?1" , "%" + nome + "%").list();
    }

    public Estado findBySigla(String sigla){
        return find("sigla LIKE ?1" , "%" + sigla.toUpperCase() + "%").firstResult();
    }

}

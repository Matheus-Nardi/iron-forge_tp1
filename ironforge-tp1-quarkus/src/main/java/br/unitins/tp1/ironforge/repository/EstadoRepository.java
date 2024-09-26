package br.unitins.tp1.ironforge.repository;

import java.util.List;

import br.unitins.tp1.ironforge.model.Estado;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EstadoRepository implements PanacheRepository<Estado> {

    //JPQL -HQL
    public List<Estado> findByNome(String nome){
        return find("select e from Estado e WHERE e.nome LIKE ?1" , "%" + nome + "%").list();
    }
}
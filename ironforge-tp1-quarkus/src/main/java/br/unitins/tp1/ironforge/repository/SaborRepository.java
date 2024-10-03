package br.unitins.tp1.ironforge.repository;

import java.util.List;

import br.unitins.tp1.ironforge.model.whey.Sabor;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SaborRepository implements PanacheRepository<Sabor> {

    public List<Sabor> findByNome(String nome){
        return find("select s from Sabor s WHERE s.nome LIKE ?1" , "%" + nome + "%").list();
    }
}

package br.unitins.tp1.ironforge.repository;

import java.util.List;

import br.unitins.tp1.ironforge.model.Cidade;
import br.unitins.tp1.ironforge.model.Estado;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CidadeRepository implements PanacheRepository<Cidade> {

    public List<Cidade> findByNome(String nome){
        return find("select c from Cidade c WHERE c.nome LIKE ?1" , "%" + nome + "%").list();
    }
    public List<Cidade> findByEstado(Estado estado){
        return find("select c from Cidade c WHERE c.estado.id = ?1" , estado.getId()).list();
    }


}

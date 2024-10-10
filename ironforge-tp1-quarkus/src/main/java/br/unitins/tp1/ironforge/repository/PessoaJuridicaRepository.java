package br.unitins.tp1.ironforge.repository;

import java.util.List;

import br.unitins.tp1.ironforge.model.usuario.PessoaJuridica;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PessoaJuridicaRepository implements PanacheRepository<PessoaJuridica> {

    //JPQL -HQL
    public List<PessoaJuridica> findByNome(String nome){
        return find("nome LIKE ?1" , "%" + nome + "%").list();
    }
}

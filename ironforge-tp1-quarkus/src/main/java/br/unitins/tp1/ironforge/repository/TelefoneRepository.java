package br.unitins.tp1.ironforge.repository;

import br.unitins.tp1.ironforge.model.usuario.Telefone;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TelefoneRepository implements PanacheRepository<Telefone> {

}

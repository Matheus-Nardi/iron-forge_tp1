package br.unitins.tp1.ironforge.repository;

import br.unitins.tp1.ironforge.model.usuario.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente> {

  
}

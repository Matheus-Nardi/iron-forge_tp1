package br.unitins.tp1.ironforge.repository;

import br.unitins.tp1.ironforge.model.pagamento.Cartao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CartaoRepository implements PanacheRepository<Cartao> {

}

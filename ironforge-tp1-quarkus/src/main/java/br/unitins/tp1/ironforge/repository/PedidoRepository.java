package br.unitins.tp1.ironforge.repository;

import br.unitins.tp1.ironforge.model.Pedido;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PedidoRepository implements PanacheRepository<Pedido> {

    public Pedido findByUsuario(Long idCliente) {
        return find("cliente.id = ?1", idCliente).firstResult();
    }

}

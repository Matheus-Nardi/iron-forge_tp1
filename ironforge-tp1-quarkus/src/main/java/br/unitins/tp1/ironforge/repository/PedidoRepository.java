package br.unitins.tp1.ironforge.repository;

import java.util.List;

import br.unitins.tp1.ironforge.model.pedido.Pedido;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PedidoRepository implements PanacheRepository<Pedido> {

    public List<Pedido> findByCliente(Long idCliente) {
        return find("cliente.id = ?1", idCliente).list();
    }

    public List<Pedido> findPedidoWherePagamentoIsNullAndNotCanceled() {
        return find("SELECT p FROM Pedido p JOIN p.statusPedidos s WHERE s.situacao != 6 AND p.pagamento IS NULL").list();
    }

}

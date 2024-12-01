package br.unitins.tp1.ironforge.service.pedido;

import java.util.List;

import br.unitins.tp1.ironforge.dto.pedido.PedidoRequestDTO;
import br.unitins.tp1.ironforge.model.pedido.Pedido;
import br.unitins.tp1.ironforge.model.pedido.Situacao;

public interface PedidoService {

    Pedido findById(Long id);

    List<Pedido> findByUsername(String username);

    Pedido create(PedidoRequestDTO dto, String username);

    Pedido detailsPedido(Long id, String username);

    List<Pedido> eligbleReviews(Long idCliente, Long idWhey);

    // Implementar os patch's

    void updateStatusPedido(Long id, Situacao situacao);

    void verifyIfPaymentIsNull();

    void cancelPedido(String username, Long id);

    void returnPedido(String username, Long id);
    // pensar no cancelar

}

package br.unitins.tp1.ironforge.service.pedido;

import java.util.List;

import br.unitins.tp1.ironforge.dto.pedido.PedidoRequestDTO;
import br.unitins.tp1.ironforge.model.Pedido;

public interface PedidoService {

    Pedido findById(Long id);

    List<Pedido> findByUsername(String username);

    Pedido create(PedidoRequestDTO dto);

    // Implementar os patch's

    // pensar no cancelar

}

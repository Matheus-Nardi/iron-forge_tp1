package br.unitins.tp1.ironforge.dto.pedido;

import java.util.List;

import br.unitins.tp1.ironforge.dto.itempedido.ItemPedidoRequestDTO;
import jakarta.validation.constraints.NotNull;

public record PedidoRequestDTO(
        @NotNull(message = "O campo valor total deve ser informado")
        Double valorTotal,
        List<ItemPedidoRequestDTO> itensPedidos 
)
        

{

}

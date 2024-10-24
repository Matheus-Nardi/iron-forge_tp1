package br.unitins.tp1.ironforge.dto.itempedido;

import br.unitins.tp1.ironforge.model.ItemPedido;

public record ItemPedidoResponseDTO(
        Long idProduto,
        String nome,
        Integer quantidade,
        Double valor
        )

{
    public static ItemPedidoResponseDTO valueOf(ItemPedido itemPedido){
        return new ItemPedidoResponseDTO(itemPedido.getLote().getWheyProtein().getId(), itemPedido.getLote().getWheyProtein().getNome(), itemPedido.getQuantidade(), itemPedido.getPreco());
    }
}

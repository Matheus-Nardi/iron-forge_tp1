package br.unitins.tp1.ironforge.service.pedido;

import java.util.List;

import br.unitins.tp1.ironforge.dto.pedido.PedidoRequestDTO;
import br.unitins.tp1.ironforge.model.pagamento.Boleto;
import br.unitins.tp1.ironforge.model.pagamento.Pagamento;
import br.unitins.tp1.ironforge.model.pagamento.Pix;
import br.unitins.tp1.ironforge.model.pedido.Pedido;
import br.unitins.tp1.ironforge.model.pedido.Situacao;

public interface PedidoService {

    Pedido findById(Long id);

    List<Pedido> findByUsername(String username);

    Pedido create(PedidoRequestDTO dto, String username);

    // Implementar os patch's

    void updateStatusPedido(Long id, Situacao situacao);

    // pensar no cancelar

    Pix gerarPix(Long id);

    Boleto gerarBoleto(Long id);

    void pagar(Pagamento pagamento);

}

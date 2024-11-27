package br.unitins.tp1.ironforge.service;

import br.unitins.tp1.ironforge.model.pagamento.Boleto;
import br.unitins.tp1.ironforge.model.pagamento.Pagamento;
import br.unitins.tp1.ironforge.model.pagamento.Pix;
import br.unitins.tp1.ironforge.model.pagamento.TipoPagamento;

public interface PagamentoService {

    Pagamento findById(Long id);

    Pix gerarPix(Long idPedido, String username);

    Boleto gerarBoleto(Long idPedido, String username);

    void pagar(Long idPedido, String username, String identificador, TipoPagamento tipoPagamento);

    void pagarCartao(Long idPedido, String username, Long idCartao);

}

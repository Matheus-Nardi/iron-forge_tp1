package br.unitins.tp1.ironforge.service;

import java.util.List;

import br.unitins.tp1.ironforge.dto.pagamento.CartaoRequestDTO;
import br.unitins.tp1.ironforge.model.pagamento.Cartao;

public interface CartaoService {

    Cartao create(String username, CartaoRequestDTO dto);

    Cartao findById(Long id);

    List<Cartao> findByCliente(String username);

    List<Cartao> findAll();

    void update(String username, Long idCartao, CartaoRequestDTO dto);

    void delete(String username, Long id);

}

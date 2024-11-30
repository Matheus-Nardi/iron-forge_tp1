package br.unitins.tp1.ironforge.service.avaliacao;

import java.util.List;

import br.unitins.tp1.ironforge.dto.avaliacao.AvaliacaoRequestDTO;
import br.unitins.tp1.ironforge.model.avaliacao.Avaliacao;

public interface AvaliacaoService {

    Avaliacao findById(Long id);

    List<Avaliacao> findByWhey(Long idWhey);

    List<Avaliacao> findAll(String username);

    Avaliacao create(AvaliacaoRequestDTO dto, String username);

    void update(Long id, AvaliacaoRequestDTO dto, String username);

    Long getQtdReview(Long id);

    boolean customerHasAlreadyReviewedProduct(Long idCliente, Long idWhey);

    void delete(Long id, String username);

}

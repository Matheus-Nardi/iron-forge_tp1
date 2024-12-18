package br.unitins.tp1.ironforge.service.lote;

import java.util.List;

import br.unitins.tp1.ironforge.dto.lote.LoteRequestDTO;
import br.unitins.tp1.ironforge.model.pedido.Lote;

public interface LoteService {

    Lote findById(Long id);

    Lote findByCodigo(String codigo);

    Lote findByWhey(Long idWhey);

    List<Lote> findAll();

    Lote create(LoteRequestDTO dto);

    Integer findByIdWheyQuantTotal(Long idWhey);

    void update(Long id, LoteRequestDTO dto);

    void delete(Long id);

}

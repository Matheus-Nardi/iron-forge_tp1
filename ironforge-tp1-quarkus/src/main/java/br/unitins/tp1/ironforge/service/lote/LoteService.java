package br.unitins.tp1.ironforge.service.lote;

import java.util.List;

import br.unitins.tp1.ironforge.dto.lote.LoteRequestDTO;
import br.unitins.tp1.ironforge.model.Lote;

public interface LoteService {

    Lote findById(Long id);

    List<Lote> findByCodigo(String codigo);

    List<Lote> findByWhey(Long idWhey);

    List<Lote> findAll();

    Lote create(LoteRequestDTO dto);

    void update(Long id, LoteRequestDTO dto);

    void delete(Long id);

}

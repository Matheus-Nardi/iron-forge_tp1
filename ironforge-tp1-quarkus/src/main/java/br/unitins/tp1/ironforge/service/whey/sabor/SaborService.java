package br.unitins.tp1.ironforge.service.whey.sabor;

import java.util.List;

import br.unitins.tp1.ironforge.dto.whey.SaborRequestDTO;
import br.unitins.tp1.ironforge.model.whey.Sabor;

public interface SaborService {

    Sabor findById(Long id);

    List<Sabor> findByNome(String nome);

    List<Sabor> findAll();

    Sabor create(SaborRequestDTO dto);

    void update(Long id, SaborRequestDTO dto);

    void delete(Long id);

}

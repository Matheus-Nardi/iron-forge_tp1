package br.unitins.tp1.ironforge.service.fabricante;

import java.util.List;

import br.unitins.tp1.ironforge.dto.FabricanteRequestDTO;
import br.unitins.tp1.ironforge.model.Fabricante;

public interface FabricanteService {

    Fabricante findById(Long id);

    List<Fabricante> findByNome(String nome);

    List<Fabricante> findAll();

    Fabricante create(FabricanteRequestDTO dto);

    void update(Long id, FabricanteRequestDTO dto);

    void delete(Long id);

}

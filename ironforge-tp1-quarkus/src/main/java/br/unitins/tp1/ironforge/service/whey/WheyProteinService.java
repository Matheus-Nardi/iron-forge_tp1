package br.unitins.tp1.ironforge.service.whey;

import java.util.List;

import br.unitins.tp1.ironforge.dto.whey.WheyProteinRequestDTO;
import br.unitins.tp1.ironforge.model.whey.WheyProtein;

public interface WheyProteinService {

    WheyProtein findById(Long id);

    List<WheyProtein> findByNome(String nome);

    List<WheyProtein> findByPreco(Double preco);

    List<WheyProtein> findAll();

    WheyProtein create(WheyProteinRequestDTO dto);

    void update(Long id, WheyProteinRequestDTO dto);

    void delete(Long id);
}

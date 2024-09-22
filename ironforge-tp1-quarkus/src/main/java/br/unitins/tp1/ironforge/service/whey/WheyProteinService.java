package br.unitins.tp1.ironforge.service.whey;

import java.util.List;

import br.unitins.tp1.ironforge.dto.whey.WheyProteinDTO;
import br.unitins.tp1.ironforge.dto.whey.WheyProteinResponseDTO;

public interface WheyProteinService {

    WheyProteinResponseDTO findById(Long id);

    List<WheyProteinResponseDTO> findByNome(String nome);

    List<WheyProteinResponseDTO> findByPreco(Double preco);

    List<WheyProteinResponseDTO> findAll();

    WheyProteinResponseDTO create(WheyProteinDTO wheyProtein);

    void update(Long id, WheyProteinDTO wheyProtein);

    void delete(Long id);
}

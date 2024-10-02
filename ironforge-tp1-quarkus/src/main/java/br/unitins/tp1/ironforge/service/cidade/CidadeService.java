package br.unitins.tp1.ironforge.service.cidade;

import java.util.List;

import br.unitins.tp1.ironforge.dto.cidade.CidadeDTO;
import br.unitins.tp1.ironforge.dto.cidade.CidadeResponseDTO;

public interface CidadeService {

    CidadeResponseDTO findById(Long id);

    List<CidadeResponseDTO> findByNome(String nome);

    List<CidadeResponseDTO> findAll();

    CidadeResponseDTO create(CidadeDTO dto);

    CidadeResponseDTO update(Long id, CidadeDTO dto);

    void delete(Long id);

}

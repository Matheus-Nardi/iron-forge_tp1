package br.unitins.tp1.ironforge.service.estado;

import java.util.List;

import br.unitins.tp1.ironforge.dto.estado.EstadoDTO;
import br.unitins.tp1.ironforge.dto.estado.EstadoResponseDTO;

public interface EstadoService {

    EstadoResponseDTO findById(Long id);

    List<EstadoResponseDTO> findByNome(String nome);

    List<EstadoResponseDTO> findAll();

    EstadoResponseDTO create(EstadoDTO dto);

    EstadoResponseDTO update(Long id, EstadoDTO dto);

    void delete(Long id);

}

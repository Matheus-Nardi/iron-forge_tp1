package br.unitins.tp1.ironforge.service.estado;

import java.util.List;

import br.unitins.tp1.ironforge.dto.estado.EstadoRequestDTO;
import br.unitins.tp1.ironforge.model.endereco.Estado;

public interface EstadoService {

    Estado findById(Long id);

    List<Estado> findByNome(String nome);

    Estado findBySigla(String sigla);

    List<Estado> findAll();

    Estado create(EstadoRequestDTO dto);

    Estado update(Long id, EstadoRequestDTO dto);

    void delete(Long id);

}

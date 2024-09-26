package br.unitins.tp1.ironforge.service;

import java.util.List;

import br.unitins.tp1.ironforge.dto.cidade.CidadeRequestDTO;
import br.unitins.tp1.ironforge.model.Cidade;

public interface CidadeService {

    Cidade findById(Long id);

    List<Cidade> findByNome(String nome);

    List<Cidade> findAll();

    Cidade create(CidadeRequestDTO dto);

    Cidade update(Long id, CidadeRequestDTO dto);

    void delete(Long id);

}

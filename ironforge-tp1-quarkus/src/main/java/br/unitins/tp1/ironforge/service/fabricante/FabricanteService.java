package br.unitins.tp1.ironforge.service.fabricante;

import java.util.List;

import br.unitins.tp1.ironforge.dto.endereco.EnderecoRequestDTO;
import br.unitins.tp1.ironforge.dto.pessoajuridica.FabricanteRequestDTO;
import br.unitins.tp1.ironforge.dto.pessoajuridica.FabricanteUpdateRequestDTO;
import br.unitins.tp1.ironforge.dto.telefone.TelefoneRequestDTO;
import br.unitins.tp1.ironforge.model.Fabricante;

public interface FabricanteService {

    Fabricante findById(Long id);

    List<Fabricante> findByNome(String nome);

    List<Fabricante> findAll();

    Fabricante create(FabricanteRequestDTO dto);

    void update(Long id, FabricanteUpdateRequestDTO dto);

    void updateTelefone(Long id, Long idTelefone, TelefoneRequestDTO dto);

    void updateEndereco(Long id, Long idEndereco, EnderecoRequestDTO dto);

    void delete(Long id);

}

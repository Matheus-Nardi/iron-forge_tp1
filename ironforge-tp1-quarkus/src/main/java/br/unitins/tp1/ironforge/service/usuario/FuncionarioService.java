package br.unitins.tp1.ironforge.service.usuario;

import java.util.List;

import br.unitins.tp1.ironforge.dto.usuario.funcionario.FuncionarioRequestDTO;
import br.unitins.tp1.ironforge.model.usuario.Funcionario;

public interface FuncionarioService {

    Funcionario findById(Long id);

    List<Funcionario> findByNome(String nome);

    List<Funcionario> findAll();

    Funcionario create(FuncionarioRequestDTO dto);

    void update(Long id, FuncionarioRequestDTO dto);

    void delete(Long id);

}

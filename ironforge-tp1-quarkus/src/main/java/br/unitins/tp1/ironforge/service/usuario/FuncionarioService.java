package br.unitins.tp1.ironforge.service.usuario;

import java.util.List;

import br.unitins.tp1.ironforge.dto.endereco.EnderecoRequestDTO;
import br.unitins.tp1.ironforge.dto.telefone.TelefoneRequestDTO;
import br.unitins.tp1.ironforge.dto.usuario.funcionario.FuncionarioCreateRequestDTO;
import br.unitins.tp1.ironforge.dto.usuario.funcionario.FuncionarioUpdateRequestDTO;
import br.unitins.tp1.ironforge.model.usuario.Funcionario;

public interface FuncionarioService {

    Funcionario findById(Long id);

    List<Funcionario> findByNome(String nome);

    List<Funcionario> findAll();

    Funcionario create(FuncionarioCreateRequestDTO dto);

    void update(Long id, FuncionarioUpdateRequestDTO dto);

    void delete(Long id);

    void updateTelefone(Long id, Long idTelefone, TelefoneRequestDTO dto);

    void updateEndereco(Long id, Long idEndereco, EnderecoRequestDTO dto);

}

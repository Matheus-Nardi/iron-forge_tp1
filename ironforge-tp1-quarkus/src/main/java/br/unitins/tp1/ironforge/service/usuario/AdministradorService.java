package br.unitins.tp1.ironforge.service.usuario;

import br.unitins.tp1.ironforge.dto.pessoafisica.FuncionarioBasicoRequestDTO;
import br.unitins.tp1.ironforge.model.usuario.Cliente;
import br.unitins.tp1.ironforge.model.usuario.Funcionario;

public interface AdministradorService {

    Funcionario transformarClienteEmFuncionario(Long idCliente, FuncionarioBasicoRequestDTO dto);

    Cliente transformarFuncionarioEmCliente(Long idFuncionario);

}

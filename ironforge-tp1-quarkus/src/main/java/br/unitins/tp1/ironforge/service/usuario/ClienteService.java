package br.unitins.tp1.ironforge.service.usuario;

import java.util.List;

import br.unitins.tp1.ironforge.dto.endereco.EnderecoRequestDTO;
import br.unitins.tp1.ironforge.dto.telefone.TelefoneRequestDTO;
import br.unitins.tp1.ironforge.dto.usuario.cliente.ClienteCreateRequestDTO;
import br.unitins.tp1.ironforge.dto.usuario.cliente.ClienteUpdateRequestDTO;
import br.unitins.tp1.ironforge.model.usuario.Cliente;

public interface ClienteService {

    Cliente findById(Long id);

    List<Cliente> findByNome(String nome);

    List<Cliente> findAll();

    Cliente create(ClienteCreateRequestDTO dto);

    void update(Long id, ClienteUpdateRequestDTO dto);

    void delete(Long id);

    void updateTelefone(Long id, Long idTelefone, TelefoneRequestDTO dto);

    void updateEndereco(Long id, Long idEndereco, EnderecoRequestDTO dto);

}

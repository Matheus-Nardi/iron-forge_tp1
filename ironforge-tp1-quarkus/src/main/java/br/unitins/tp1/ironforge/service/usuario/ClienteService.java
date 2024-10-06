package br.unitins.tp1.ironforge.service.usuario;

import java.util.List;

import br.unitins.tp1.ironforge.dto.usuario.cliente.ClienteRequestDTO;
import br.unitins.tp1.ironforge.model.usuario.Cliente;

public interface ClienteService {

    Cliente findById(Long id);

    List<Cliente> findByNome(String nome);

    List<Cliente> findAll();

    Cliente create(ClienteRequestDTO dto);

    void update(Long id, ClienteRequestDTO dto);

    void delete(Long id);

}

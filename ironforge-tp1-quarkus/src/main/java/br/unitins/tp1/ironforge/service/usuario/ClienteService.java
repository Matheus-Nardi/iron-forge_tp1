package br.unitins.tp1.ironforge.service.usuario;

import java.util.List;

import br.unitins.tp1.ironforge.dto.usuario.UsuarioRequestDTO;
import br.unitins.tp1.ironforge.model.usuario.Cliente;

public interface ClienteService {

    Cliente findById(Long id);

    List<Cliente> findByNome(String nome);

    List<Cliente> findAll();

    Cliente create(UsuarioRequestDTO dto);

    void update(Long id, UsuarioRequestDTO dto);

    void delete(Long id);

}

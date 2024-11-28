package br.unitins.tp1.ironforge.service.usuario;

import java.util.List;

import br.unitins.tp1.ironforge.dto.usuario.UsuarioRequestDTO;
import br.unitins.tp1.ironforge.model.usuario.Usuario;

public interface UsuarioService {

    Usuario findById(Long id);

    Usuario create(UsuarioRequestDTO dto);

    List<Usuario> findAll();

    Usuario findByUsernameAndSenha(String username, String senha);

    Usuario findByUsername(String username);

}

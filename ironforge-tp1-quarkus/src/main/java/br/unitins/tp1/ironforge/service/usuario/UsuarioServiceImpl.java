package br.unitins.tp1.ironforge.service.usuario;

import java.util.List;

import br.unitins.tp1.ironforge.model.usuario.Usuario;
import br.unitins.tp1.ironforge.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    public UsuarioRepository usuarioRepository;

    @Override
    public Usuario findByUsernameAndSenha(String username, String senha) {
        return usuarioRepository.findByUsernameAndSenha(username, senha);
    }

    @Override
    public Usuario findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<Usuario> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

}

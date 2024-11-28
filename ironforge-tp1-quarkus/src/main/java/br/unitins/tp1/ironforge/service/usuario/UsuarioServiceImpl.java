package br.unitins.tp1.ironforge.service.usuario;

import java.time.LocalDate;
import java.util.List;

import br.unitins.tp1.ironforge.dto.usuario.UsuarioRequestDTO;
import br.unitins.tp1.ironforge.model.usuario.Usuario;
import br.unitins.tp1.ironforge.repository.UsuarioRepository;
import br.unitins.tp1.ironforge.service.hash.HashService;
import br.unitins.tp1.ironforge.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    public UsuarioRepository usuarioRepository;

    @Inject
    public HashService hashService;

    @Override
    public Usuario findByUsernameAndSenha(String username, String senha) {
        return usuarioRepository.findByUsernameAndSenha(username, senha);
    }

    @Override
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll().list();
    }

    @Override
    public Usuario findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public Usuario create(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        if (existeUsername(dto.username())) {
            throw new ValidationException("username", "O username é invalido");
        }
        usuario.setCpf(dto.cpf());
        usuario.setUsername(dto.username());
        usuario.setPerfil(dto.perfil());
        usuario.setSenha(hashService.getHashSenha(dto.senha()));
        usuario.setDataCadastro(LocalDate.now());
        usuarioRepository.persist(usuario);

        return usuario;
    }

    private boolean existeUsername(String username) {
        Usuario usuario = findByUsername(username);
        if (usuario != null) {
            return true;
        }

        return false;
    }

}

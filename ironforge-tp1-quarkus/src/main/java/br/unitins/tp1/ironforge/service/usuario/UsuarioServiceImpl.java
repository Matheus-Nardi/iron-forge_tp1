package br.unitins.tp1.ironforge.service.usuario;

import java.time.LocalDate;
import java.util.List;

import br.unitins.tp1.ironforge.dto.usuario.EmailPatchDTO;
import br.unitins.tp1.ironforge.dto.usuario.SenhaPatchDTO;
import br.unitins.tp1.ironforge.dto.usuario.UsuarioRequestDTO;
import br.unitins.tp1.ironforge.model.Perfil;
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
        if (existeUsername(dto.username())) {
            throw new ValidationException("username", "O username é invalido");
        }
        if (existeEmail(dto.email())) {
            throw new ValidationException("email", "O email é invalido");
        }
        Usuario usuario = new Usuario();
        usuario.setUsername(dto.username());
        usuario.setEmail(dto.email());
        usuario.setListaPerfil(List.of(Perfil.CLIENTE));
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

    private boolean existeEmail(String email) {
        return usuarioRepository.findByEmail(email) != null;
    }

    @Override
    @Transactional
    public void updateEmail(Long id, EmailPatchDTO dto) {
        Usuario usuario = findById(id);

        if (existeEmail(dto.email())) {
            throw new ValidationException("email", "O email é invalido");
        }

        usuario.setEmail(dto.email());

    }

    @Override
    @Transactional
    public void updateSenha(Long id, SenhaPatchDTO dto) {
        Usuario usuario = findById(id);
        usuario.setSenha(hashService.getHashSenha(dto.senha()));
    }

}

package br.unitins.tp1.ironforge.service.usuario;

import java.util.List;

import br.unitins.tp1.ironforge.dto.usuario.funcionario.FuncionarioRequestDTO;
import br.unitins.tp1.ironforge.model.usuario.Funcionario;
import br.unitins.tp1.ironforge.model.usuario.Usuario;
import br.unitins.tp1.ironforge.repository.FuncionarioRepository;
import br.unitins.tp1.ironforge.service.cidade.CidadeService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class FuncionarioServiceImpl implements FuncionarioService {

    @Inject
    public FuncionarioRepository funcionarioRepository;

    @Inject
    public UsuarioService usuarioService;

    @Inject
    public CidadeService cidadeService;

    @Override
    public Funcionario findById(Long id) {
        return funcionarioRepository.findById(id);
    }

    @Override
    public List<Funcionario> findByNome(String nome) {
        List<Usuario> usuarios = usuarioService.findFuncionarioByNome(nome);
        return usuarios.stream().map(u -> valueOf(u)).toList();
    }

    @Override
    public List<Funcionario> findAll() {
        return funcionarioRepository.listAll();
    }

    @Override
    @Transactional
    public Funcionario create(FuncionarioRequestDTO dto) {
        usuarioService.validateCredentials(dto.usuario().cpf(), dto.usuario().email());
        Funcionario funcionario = toEntity(dto);
        usuarioService.create(funcionario.getUsuario());
        funcionarioRepository.persist(funcionario);
        return funcionario;
    }

    @Override
    @Transactional
    public void update(Long id, FuncionarioRequestDTO dto) {
        usuarioService.validateCredentials(dto.usuario().cpf(), dto.usuario().email());
        Funcionario funcionario = funcionarioRepository.findById(id);
        Usuario usuario = funcionario.getUsuario();

        usuario.setNome(dto.usuario().nome());
        usuario.setCpf(dto.usuario().cpf());
        usuario.setEmail(dto.usuario().email());
        usuario.setSenha(dto.usuario().senha());
        usuario.setDataNascimento(dto.usuario().dataNascimento());
        funcionario.setSalario(dto.salario());

        funcionario.setUsuario(usuario);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        funcionarioRepository.deleteById(id);
    }

    private Funcionario valueOf(Usuario usuario) {
        Funcionario f = new Funcionario();
        f.setId(usuario.getId());
        f.setUsuario(usuario);
        return f;
    }

    private Funcionario toEntity(FuncionarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        Funcionario funcionario = new Funcionario();
        usuario.setNome(dto.usuario().nome());
        usuario.setCpf(dto.usuario().cpf());
        usuario.setEmail(dto.usuario().email());
        usuario.setSenha(dto.usuario().senha());
        usuario.setDataNascimento(dto.usuario().dataNascimento());
        usuario.setTelefones(usuarioService.getTelefones(dto.usuario()));
        usuario.setEnderecos(usuarioService.getEnderecos(dto.usuario()));
        funcionario.setSalario(dto.salario());
        funcionario.setUsuario(usuario);
        return funcionario;
    }

}

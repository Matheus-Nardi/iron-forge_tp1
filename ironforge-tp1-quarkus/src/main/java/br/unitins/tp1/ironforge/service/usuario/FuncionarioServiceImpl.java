package br.unitins.tp1.ironforge.service.usuario;

import java.util.List;

import br.unitins.tp1.ironforge.dto.usuario.funcionario.FuncionarioRequestDTO;
import br.unitins.tp1.ironforge.model.usuario.Funcionario;
import br.unitins.tp1.ironforge.model.usuario.Usuario;
import br.unitins.tp1.ironforge.repository.FuncionarioRepository;
import br.unitins.tp1.ironforge.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class FuncionarioServiceImpl implements FuncionarioService {

    @Inject
    public FuncionarioRepository funcionarioRepository;
    @Inject
    public UsuarioRepository usuarioRepository;

    @Override
    public Funcionario findById(Long id) {
        return funcionarioRepository.findById(id);
    }

    @Override
    public List<Funcionario> findByNome(String nome) {
        List<Usuario> usuarios = usuarioRepository.findFuncionarioByNome(nome);
        return usuarios.stream().map(u -> valueOf(u)).toList();
    }

    @Override
    public List<Funcionario> findAll() {
        return funcionarioRepository.listAll();
    }

    @Override
    @Transactional
    public Funcionario create(FuncionarioRequestDTO dto) {
        validateCredentials(dto);
        Funcionario funcionario = FuncionarioRequestDTO.toEntity(dto);
        usuarioRepository.persist(funcionario.getUsuario());
        funcionarioRepository.persist(funcionario);
        return funcionario;
    }

    @Override
    @Transactional
    public void update(Long id, FuncionarioRequestDTO dto) {
        validateCredentials(dto);
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

    private void validateCredentials(FuncionarioRequestDTO funcionario) {
        if (usuarioRepository.existByCpf(funcionario.usuario().cpf()))
            throw new IllegalArgumentException("Já existe um usuário cadastrado com esse CPF");

        if (usuarioRepository.existByEmail(funcionario.usuario().email()))
            throw new IllegalArgumentException("Já existe um usuário cadastrado com esse email");
    }

}

package br.unitins.tp1.ironforge.service.usuario;

import java.util.ArrayList;
import java.util.List;

import br.unitins.tp1.ironforge.dto.endereco.EnderecoRequestDTO;
import br.unitins.tp1.ironforge.dto.telefone.TelefoneRequestDTO;
import br.unitins.tp1.ironforge.dto.usuario.funcionario.FuncionarioCreateRequestDTO;
import br.unitins.tp1.ironforge.dto.usuario.funcionario.FuncionarioUpdateRequestDTO;
import br.unitins.tp1.ironforge.model.Endereco;
import br.unitins.tp1.ironforge.model.Telefone;
import br.unitins.tp1.ironforge.model.usuario.Funcionario;
import br.unitins.tp1.ironforge.model.usuario.Usuario;
import br.unitins.tp1.ironforge.repository.FuncionarioRepository;
import br.unitins.tp1.ironforge.service.cidade.CidadeService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
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
        List<Funcionario> funcionarios = new ArrayList<>();
        valueOf(usuarios, funcionarios);
        return funcionarios;
    }

    @Override
    public List<Funcionario> findAll() {
        return funcionarioRepository.listAll();
    }

    @Override
    @Transactional
    public Funcionario create(FuncionarioCreateRequestDTO dto) {
        usuarioService.validateCredentials(dto.usuario().cpf(), dto.usuario().email());

        Usuario usuario = usuarioService.create(dto.usuario());
        Funcionario funcionario = new Funcionario();
        funcionario.setUsuario(usuario);
        funcionario.setSalario(dto.salario());
        funcionarioRepository.persist(funcionario);
        return funcionario;
    }

    @Override
    @Transactional
    public void update(Long id, FuncionarioUpdateRequestDTO dto) {
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
        Funcionario funcionario = funcionarioRepository.findById(id);
        if (funcionario == null) {
            throw new EntityNotFoundException("Funcionario não encontrado para o ID: " + id);
        }

        Long usuarioId = funcionario.getUsuario().getId();
        funcionarioRepository.delete(funcionario);
        usuarioService.delete(usuarioId);
    }

    private void valueOf(List<Usuario> usuarios, List<Funcionario> funcionarios) {
        for (Usuario usuario : usuarios) {
            Funcionario funcionario = new Funcionario();
            funcionario.setId(funcionarioRepository.findFuncionarioByUsuario(usuario.getId()).getId());
            funcionario.setUsuario(usuario);
            funcionarios.add(funcionario);
        }
    }

        @Override
        @Transactional
        public void updateTelefone(Long id, Long idTelefone, TelefoneRequestDTO dto) {
            Funcionario funcionario = funcionarioRepository.findById(id);
            Telefone telefone = funcionario.getUsuario().getTelefones().stream().filter(t -> t.getId().equals(idTelefone))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Telefone não encontrado"));
            telefone.setCodigoArea(dto.codigoArea());
            telefone.setNumero(dto.numero());
        }

    @Override
    @Transactional
    public void updateEndereco(Long id, Long idEndereco, EnderecoRequestDTO dto) {
        Funcionario funcionario = funcionarioRepository.findById(id);
        Endereco endereco = funcionario.getUsuario().getEnderecos().stream().filter(e -> e.getId().equals(idEndereco))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Endereco não encontrado"));

        endereco.setBairro(dto.bairro());
        endereco.setCep(dto.cep());
        endereco.setComplemento(dto.complemento());
        endereco.setLogradouro(dto.logradouro());
        endereco.setNumero(dto.numero());
        endereco.setCidade(cidadeService.findById(dto.idCidade()));
    }

}

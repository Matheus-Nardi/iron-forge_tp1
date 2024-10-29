package br.unitins.tp1.ironforge.service.usuario;

import java.util.ArrayList;
import java.util.List;

import br.unitins.tp1.ironforge.dto.endereco.EnderecoRequestDTO;
import br.unitins.tp1.ironforge.dto.pessoafisica.FuncionarioRequestDTO;
import br.unitins.tp1.ironforge.dto.pessoafisica.FuncionarioUpdateRequestDTO;
import br.unitins.tp1.ironforge.dto.telefone.TelefoneRequestDTO;
import br.unitins.tp1.ironforge.model.Endereco;
import br.unitins.tp1.ironforge.model.PessoaFisica;
import br.unitins.tp1.ironforge.model.Telefone;
import br.unitins.tp1.ironforge.model.usuario.Funcionario;
import br.unitins.tp1.ironforge.model.usuario.Usuario;
import br.unitins.tp1.ironforge.repository.FuncionarioRepository;
import br.unitins.tp1.ironforge.repository.PessoaFisicaRepository;
import br.unitins.tp1.ironforge.repository.UsuarioRepository;
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
    public PessoaFisicaRepository pessoaFisicaRepository;

    @Inject
    public UsuarioRepository usuarioRepository;

    @Inject
    public CidadeService cidadeService;

    @Override
    public Funcionario findById(Long id) {
        return funcionarioRepository.findById(id);
    }

    @Override
    public List<Funcionario> findByNome(String nome) {
        return funcionarioRepository.findFuncionarioByNome(nome);
    }

    @Override
    public List<Funcionario> findAll() {
        return funcionarioRepository.listAll();
    }

    @Override
    @Transactional
    public Funcionario create(FuncionarioRequestDTO dto) {
        Funcionario funcionario = new Funcionario();
        Usuario usuario = getUsuario(dto);

        PessoaFisica pf = getPessoaFisica(dto, usuario);
        funcionario.setPessoaFisica(pf); // Associando funcionario com pf
        funcionario.setCargo(dto.cargo());
        funcionario.setSalario(dto.salario());
        funcionario.setDataContratacao(dto.dataContratacao());
        funcionarioRepository.persist(funcionario);

        return funcionario;
    }

    private PessoaFisica getPessoaFisica(FuncionarioRequestDTO dto, Usuario usuario) {
        PessoaFisica pf = new PessoaFisica();
        pf.setUsuario(usuario); // Associando pessoa com usuario

        // Defenindo pessoa fisica
        pf.setNome(dto.nome());
        pf.setEmail(dto.email());
        pf.setCpf(dto.cpf());
        pf.setDataNascimento(dto.dataNascimento());
        pf.setEnderecos(getEnderecos(dto));
        pf.setTelefones(getTelefones(dto));

        pessoaFisicaRepository.persist(pf);
        return pf;
    }

    private Usuario getUsuario(FuncionarioRequestDTO dto) {
        Usuario usuario = new Usuario();

        // Definindo usuario
        usuario.setUsername(dto.usuario().username());
        usuario.setSenha(dto.usuario().senha());
        usuarioRepository.persist(usuario);
        return usuario;
    }

    @Override
    @Transactional
    public void update(Long id, FuncionarioUpdateRequestDTO dto) {

        Funcionario funcionario = funcionarioRepository.findById(id);

        if (funcionario == null)
            throw new IllegalArgumentException("Funcionario não encontrado!");
        funcionario.setCargo(dto.cargo());
        funcionario.setSalario(dto.salario());
        funcionario.setDataContratacao(dto.dataContratacao());
        PessoaFisica pf = funcionario.getPessoaFisica();
        pf.setNome(dto.nome());
        pf.setCpf(dto.cpf());
        pf.setEmail(dto.email());
        pf.setDataNascimento(dto.dataNascimento());

    }

    @Override
    @Transactional
    public void delete(Long id) {
        Funcionario funcionario = funcionarioRepository.findById(id);
        if (funcionario == null) {
            throw new EntityNotFoundException("Funcionario não encontrado para o ID: " + id);
        }

        usuarioRepository.delete(funcionario.getPessoaFisica().getUsuario());
        pessoaFisicaRepository.delete(funcionario.getPessoaFisica());
        funcionarioRepository.delete(funcionario);
    }

    @Override
    @Transactional
    public void updateTelefone(Long id, Long idTelefone, TelefoneRequestDTO dto) {
        Funcionario funcionario = funcionarioRepository.findById(id);
        Telefone telefone = funcionario.getPessoaFisica().getTelefones().stream()
                .filter(t -> t.getId().equals(idTelefone))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Telefone não encontrado"));
        telefone.setCodigoArea(dto.codigoArea());
        telefone.setNumero(dto.numero());
    }

    @Override
    @Transactional
    public void updateEndereco(Long id, Long idEndereco, EnderecoRequestDTO dto) {
        Funcionario funcionario = funcionarioRepository.findById(id);
        Endereco endereco = funcionario.getPessoaFisica().getEnderecos().stream()
                .filter(e -> e.getId().equals(idEndereco))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Endereco não encontrado"));

        endereco.setBairro(dto.bairro());
        endereco.setCep(dto.cep());
        endereco.setComplemento(dto.complemento());
        endereco.setLogradouro(dto.logradouro());
        endereco.setNumero(dto.numero());
        endereco.setCidade(cidadeService.findById(dto.idCidade()));
    }

    private List<Telefone> getTelefones(FuncionarioRequestDTO dto) {
        List<Telefone> telefones = new ArrayList<>();

        for (int i = 0; i < dto.telefones().size(); i++) {
            Telefone telefone = new Telefone();
            TelefoneRequestDTO telefoneRequestDTO = dto.telefones().get(i);
            telefone.setCodigoArea(telefoneRequestDTO.codigoArea());
            telefone.setNumero(telefoneRequestDTO.numero());
            telefones.add(telefone);
        }
        return telefones;
    }

    private List<Endereco> getEnderecos(FuncionarioRequestDTO dto) {
        List<Endereco> enderecos = new ArrayList<>();

        for (int i = 0; i < dto.enderecos().size(); i++) {
            Endereco endereco = new Endereco();
            EnderecoRequestDTO enderecoRequestDTO = dto.enderecos().get(i);
            endereco.setBairro(enderecoRequestDTO.bairro());
            endereco.setCep(enderecoRequestDTO.cep());
            endereco.setComplemento(enderecoRequestDTO.complemento());
            endereco.setLogradouro(enderecoRequestDTO.logradouro());
            endereco.setNumero(enderecoRequestDTO.numero());
            endereco.setCidade(cidadeService.findById(enderecoRequestDTO.idCidade()));
            enderecos.add(endereco);
        }
        return enderecos;
    }

}

package br.unitins.tp1.ironforge.service.usuario;

import java.util.ArrayList;
import java.util.List;

import br.unitins.tp1.ironforge.dto.endereco.EnderecoRequestDTO;
import br.unitins.tp1.ironforge.dto.pessoafisica.FuncionarioBasicoRequestDTO;
import br.unitins.tp1.ironforge.dto.pessoafisica.FuncionarioRequestDTO;
import br.unitins.tp1.ironforge.dto.pessoafisica.FuncionarioUpdateRequestDTO;
import br.unitins.tp1.ironforge.dto.telefone.TelefoneRequestDTO;
import br.unitins.tp1.ironforge.model.Endereco;
import br.unitins.tp1.ironforge.model.Telefone;
import br.unitins.tp1.ironforge.model.usuario.Cliente;
import br.unitins.tp1.ironforge.model.usuario.Funcionario;
import br.unitins.tp1.ironforge.model.usuario.PessoaFisica;
import br.unitins.tp1.ironforge.model.usuario.Usuario;
import br.unitins.tp1.ironforge.repository.FuncionarioRepository;
import br.unitins.tp1.ironforge.repository.PessoaFisicaRepository;
import br.unitins.tp1.ironforge.service.cidade.CidadeService;
import br.unitins.tp1.ironforge.service.hash.HashService;
import br.unitins.tp1.ironforge.validation.EntidadeNotFoundException;
import br.unitins.tp1.ironforge.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class FuncionarioServiceImpl implements FuncionarioService {

    @Inject
    public FuncionarioRepository funcionarioRepository;

    @Inject
    public PessoaFisicaRepository pessoaFisicaRepository;

    @Inject
    public ClienteService clienteService;

    @Inject
    public UsuarioService usuarioService;

    @Inject
    public CidadeService cidadeService;

    @Inject
    public HashService hashService;

    @Override
    public Funcionario findById(Long id) {
        Funcionario funcionario = funcionarioRepository.findById(id);

        if (funcionario == null) {
            throw new EntidadeNotFoundException("id", "Funcionario não encontrado");
        }
        return funcionario;
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
    public Funcionario create(String username, FuncionarioRequestDTO dto) {
        validarEntidade(dto);

        Funcionario funcionario = new Funcionario();
        PessoaFisica pf = new PessoaFisica();
        Usuario usuario = usuarioService.findByUsername(username);

        pf.setUsuario(usuario); // Associando pessoa com usuario

        // Defenindo pessoa fisica
        pf.setNome(dto.nome());
        pf.setEmail(dto.email());
        pf.setCpf(dto.cpf());
        pf.setDataNascimento(dto.dataNascimento());
        pf.setEnderecos(getEnderecos(dto));
        pf.setTelefones(getTelefones(dto));

        pessoaFisicaRepository.persist(pf);
        funcionario.setPessoaFisica(pf); // Associando funcionario com pf
        funcionarioRepository.persist(funcionario);

        return funcionario;
    }

    @Transactional
    @Override
    public Funcionario transformarClienteEmFuncionario(Long idCliente, FuncionarioBasicoRequestDTO dto) {
        // Buscar cliente existente
        Cliente cliente = clienteService.findById(idCliente);
        if (cliente == null) {
            throw new EntidadeNotFoundException("id", "Cliente não encontrado.");
        }

        PessoaFisica pessoaFisica = cliente.getPessoaFisica();
        if (pessoaFisica == null) {
            throw new ValidationException("idCliente", "Cliente não possui uma pessoa física associada.");
        }

        // Verificar se a pessoaFisica já está associada a um funcionario existente
        // if (funcionarioRepository.existsByPessoaFisica(pessoaFisica)) {
        // throw new IllegalStateException("Essa pessoa já é um funcionário.");
        // }

        Funcionario funcionario = new Funcionario();
        funcionario.setPessoaFisica(pessoaFisica);
        funcionario.setSalario(dto.salario()); // Defina valores padrão ou conforme necessário
        funcionario.setDataContratacao(dto.dataContratacao());
        funcionario.setCargo(dto.cargo());
        funcionario.getPessoaFisica().getUsuario().setPerfil(null);

        funcionarioRepository.persist(funcionario);

        return funcionario;
    }

    @Override
    @Transactional
    public void update(Long id, FuncionarioUpdateRequestDTO dto) {

        Funcionario funcionario = funcionarioRepository.findById(id);

        if (funcionario == null)
            throw new EntidadeNotFoundException("id", "Funcionario não encontrado");

        if (existeCPF(dto.cpf())) {
            throw new ValidationException("cpf", "CPF informado é inválido");
        }

        if (existeEmail(dto.email())) {
            throw new ValidationException("email", "Email informado é inválido");
        }
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
            throw new EntidadeNotFoundException("id", "Funcionario não encontrado");
        }

        funcionarioRepository.delete(funcionario);
    }

    @Override
    @Transactional
    public void updateTelefone(Long id, Long idTelefone, TelefoneRequestDTO dto) {
        Funcionario funcionario = funcionarioRepository.findById(id);

        if (funcionario == null) {
            throw new EntidadeNotFoundException("id", "Funcionario não encontrado");
        }
        Telefone telefone = funcionario.getPessoaFisica().getTelefones().stream()
                .filter(t -> t.getId().equals(idTelefone))
                .findFirst()
                .orElseThrow(() -> new EntidadeNotFoundException("idTelefone", "Telefone não encontrado"));
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
                .orElseThrow(() -> new EntidadeNotFoundException("idEndereco", "Endereco não encontrado"));

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

    @Override
    @Transactional
    public void updateNomeImagem(String username, String nomeImagem) {
        Funcionario funcionario = funcionarioRepository.findFuncionarioByUsername(username);
        funcionario.getPessoaFisica().setFotoPerfil(nomeImagem);
    }

    @Override
    public Funcionario findByUsermame(String username) {
        return funcionarioRepository.findFuncionarioByUsername(username);
    }

    private void validarEntidade(FuncionarioRequestDTO dto) {
        if (existeUsuario(dto.usuario().username())) {
            throw new ValidationException("usuario.username", "Username inválido");
        }

        if (existeCPF(dto.cpf())) {
            throw new ValidationException("cnpj", "Cnpj informado é inválido");
        }
        if (existeEmail(dto.email())) {
            throw new ValidationException("email", "Email informado é inválido");
        }
    }

    private boolean existeUsuario(String username) {
        return funcionarioRepository.findFuncionarioByUsername(username) == null ? false : true;
    }

    private boolean existeCPF(String cnpj) {
        return funcionarioRepository.findFuncionarioByCpf(cnpj) == null ? false : true;
    }

    private boolean existeEmail(String email) {
        return funcionarioRepository.findFuncionarioByEmail(email) == null ? false : true;
    }

}

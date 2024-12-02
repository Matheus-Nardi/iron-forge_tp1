package br.unitins.tp1.ironforge.service.usuario;

import br.unitins.tp1.ironforge.dto.pessoafisica.FuncionarioBasicoRequestDTO;
import br.unitins.tp1.ironforge.model.Perfil;
import br.unitins.tp1.ironforge.model.usuario.Cliente;
import br.unitins.tp1.ironforge.model.usuario.Funcionario;
import br.unitins.tp1.ironforge.model.usuario.PessoaFisica;
import br.unitins.tp1.ironforge.repository.ClienteRepository;
import br.unitins.tp1.ironforge.repository.FuncionarioRepository;
import br.unitins.tp1.ironforge.service.hash.HashService;
import br.unitins.tp1.ironforge.validation.EntidadeNotFoundException;
import br.unitins.tp1.ironforge.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class AdministradorServiceImpl implements AdministradorService {

    @Inject
    public ClienteService clienteService;

    @Inject
    public FuncionarioService funcionarioService;

    @Inject
    public FuncionarioRepository funcionarioRepository;

    @Inject
    public ClienteRepository clienteRepository;

    @Inject
    public UsuarioService usuarioService;

    @Inject
    public HashService hashService;

    @Transactional
    @Override
    public Funcionario transformarClienteEmFuncionario(Long idCliente, FuncionarioBasicoRequestDTO dto) {

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
        funcionario.getPessoaFisica().getUsuario().getListaPerfil().add(Perfil.FUNCIONARIO);

        funcionarioRepository.persist(funcionario);

        return funcionario;
    }

    @Transactional
    @Override
    public Cliente transformarFuncionarioEmCliente(Long idFuncionario) {

        // Buscar o funcionário pelo ID
        Funcionario funcionario = funcionarioRepository.findById(idFuncionario);
        if (funcionario == null) {
            throw new EntidadeNotFoundException("id", "Funcionário não encontrado.");
        }

        PessoaFisica pessoaFisica = funcionario.getPessoaFisica();
        if (pessoaFisica == null) {
            throw new ValidationException("idFuncionario", "Funcionário não possui uma pessoa física associada.");
        }

        // Criar uma nova instância de Cliente
        Cliente cliente = new Cliente();
        cliente.setPessoaFisica(pessoaFisica);

        // Atualizar o perfil do usuário associado à PessoaFisica
        pessoaFisica.getUsuario().getListaPerfil().add(Perfil.CLIENTE);

        // Persistir o novo cliente
        clienteRepository.persist(cliente);

        return cliente;
    }

}

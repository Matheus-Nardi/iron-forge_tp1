package br.unitins.tp1.ironforge.service.usuario;

import java.util.ArrayList;

import br.unitins.tp1.ironforge.dto.pessoafisica.FuncionarioBasicoRequestDTO;
import br.unitins.tp1.ironforge.model.usuario.Cliente;
import br.unitins.tp1.ironforge.model.usuario.Funcionario;
import br.unitins.tp1.ironforge.model.usuario.Perfil;
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

        if (!pessoaFisica.getUsuario().getListaPerfil().contains(Perfil.FUNCIONARIO)) {
            pessoaFisica.getUsuario().getListaPerfil().add(Perfil.FUNCIONARIO);
        }

        if (funcionarioService.existsByPessoaFisica(pessoaFisica.getId())) {
            throw new ValidationException("idCLiente", "Essa pessoa já é um funcionario.");
        }

        Funcionario funcionario = new Funcionario();
        funcionario.setPessoaFisica(pessoaFisica);
        funcionario.setSalario(dto.salario());
        funcionario.setDataContratacao(dto.dataContratacao());
        funcionario.setCargo(dto.cargo());
        funcionario.getPessoaFisica().getUsuario().getListaPerfil().add(Perfil.FUNCIONARIO);

        funcionarioRepository.persist(funcionario);

        return funcionario;
    }

    @Transactional
    @Override
    public Cliente transformarFuncionarioEmCliente(Long idFuncionario) {

        Funcionario funcionario = funcionarioRepository.findById(idFuncionario);
        if (funcionario == null) {
            throw new EntidadeNotFoundException("id", "Funcionário não encontrado.");
        }

        PessoaFisica pessoaFisica = funcionario.getPessoaFisica();
        if (pessoaFisica == null) {
            throw new ValidationException("idFuncionario", "Funcionário não possui uma pessoa física associada.");
        }

        if (funcionarioService.existsByPessoaFisica(pessoaFisica.getId())) {
            throw new ValidationException("idFuncionario", "Essa pessoa já é um cliente.");
        }

        if (!pessoaFisica.getUsuario().getListaPerfil().contains(Perfil.FUNCIONARIO)) {
            pessoaFisica.getUsuario().getListaPerfil().add(Perfil.FUNCIONARIO);
        }

        Cliente cliente = new Cliente();
        cliente.setPessoaFisica(pessoaFisica);

        pessoaFisica.getUsuario().getListaPerfil().add(Perfil.CLIENTE);
        cliente.setCartoes(new ArrayList<>());
        cliente.setListaDesejos(new ArrayList<>());

        clienteRepository.persist(cliente);

        return cliente;
    }

}

package br.unitins.tp1.ironforge.service.usuario;

import java.util.ArrayList;
import java.util.List;

import br.unitins.tp1.ironforge.dto.endereco.EnderecoRequestDTO;
import br.unitins.tp1.ironforge.dto.pessoafisica.ClienteRequestDTO;
import br.unitins.tp1.ironforge.dto.pessoafisica.ClienteUpdateRequestDTO;
import br.unitins.tp1.ironforge.dto.telefone.TelefoneRequestDTO;
import br.unitins.tp1.ironforge.model.Endereco;
import br.unitins.tp1.ironforge.model.Telefone;
import br.unitins.tp1.ironforge.model.usuario.Cliente;
import br.unitins.tp1.ironforge.model.usuario.PessoaFisica;
import br.unitins.tp1.ironforge.model.usuario.Usuario;
import br.unitins.tp1.ironforge.repository.ClienteRepository;
import br.unitins.tp1.ironforge.repository.PessoaFisicaRepository;
import br.unitins.tp1.ironforge.repository.UsuarioRepository;
import br.unitins.tp1.ironforge.service.cidade.CidadeService;
import br.unitins.tp1.ironforge.service.hash.HashService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ClienteServiceImpl implements ClienteService {

    @Inject
    public ClienteRepository clienteRepository;

    @Inject
    public PessoaFisicaRepository pessoaFisicaRepository;

    @Inject
    public UsuarioRepository usuarioRepository;

    @Inject
    public CidadeService cidadeService;

    @Inject
    public HashService hashService;

    @Override
    public Cliente findById(Long id) {
        return clienteRepository.findById(id);
    }

    @Override
    public List<Cliente> findByNome(String nome) {
        return clienteRepository.findClienteByNome(nome);
    }

    @Override
    public List<Cliente> findAll() {
        return clienteRepository.listAll();
    }

    @Override
    @Transactional
    public Cliente create(ClienteRequestDTO dto) {
        Cliente cliente = new Cliente();
        PessoaFisica pf = new PessoaFisica();
        Usuario usuario = new Usuario();

        // Definindo usuario
        usuario.setUsername(dto.usuario().username());
        usuario.setSenha(hashService.getHashSenha(dto.usuario().senha()));
        usuario.setPerfil(dto.usuario().perfil());
        usuarioRepository.persist(usuario);

        pf.setUsuario(usuario); // Associando pessoa com usuario

        // Defenindo pessoa fisica
        pf.setNome(dto.nome());
        pf.setEmail(dto.email());
        pf.setCpf(dto.cpf());
        pf.setDataNascimento(dto.dataNascimento());
        pf.setEnderecos(getEnderecos(dto));
        pf.setTelefones(getTelefones(dto));

        pessoaFisicaRepository.persist(pf);
        cliente.setPessoaFisica(pf); // Associando cliente com pf
        clienteRepository.persist(cliente);

        return cliente;
    }

    @Override
    @Transactional
    public void update(Long id, ClienteUpdateRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(id);

        if (cliente == null)
            throw new IllegalArgumentException("Cliente não encontrado!");
        PessoaFisica pf = cliente.getPessoaFisica();
        pf.setNome(dto.nome());
        pf.setCpf(dto.cpf());
        pf.setEmail(dto.email());
        pf.setDataNascimento(dto.dataNascimento());
    }

    @Override
    @Transactional
    public void delete(Long clienteId) {

        Cliente cliente = clienteRepository.findById(clienteId);
        if (cliente == null) {
            throw new EntityNotFoundException("Cliente não encontrado para o ID: " + clienteId);
        }
        usuarioRepository.delete(cliente.getPessoaFisica().getUsuario());
        pessoaFisicaRepository.delete(cliente.getPessoaFisica());
        clienteRepository.delete(cliente);
    }

    @Override
    @Transactional
    public void updateTelefone(Long id, Long idTelefone, TelefoneRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(id);
        Telefone telefone = cliente.getPessoaFisica().getTelefones().stream().filter(t -> t.getId().equals(idTelefone))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Telefone não encontrado"));

        telefone.setCodigoArea(dto.codigoArea());
        telefone.setNumero(dto.numero());
    }

    @Override
    @Transactional
    public void updateEndereco(Long id, Long idEndereco, EnderecoRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(id);
        Endereco endereco = cliente.getPessoaFisica().getEnderecos().stream().filter(e -> e.getId().equals(idEndereco))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Endereco não encontrado"));

        endereco.setBairro(dto.bairro());
        endereco.setCep(dto.cep());
        endereco.setComplemento(dto.complemento());
        endereco.setLogradouro(dto.logradouro());
        endereco.setNumero(dto.numero());
        endereco.setCidade(cidadeService.findById(dto.idCidade()));

        cliente.getPessoaFisica().setEnderecos(cliente.getPessoaFisica().getEnderecos());

    }

    private List<Telefone> getTelefones(ClienteRequestDTO dto) {
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

    private List<Endereco> getEnderecos(ClienteRequestDTO dto) {
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

package br.unitins.tp1.ironforge.service.usuario;

import java.util.List;

import br.unitins.tp1.ironforge.dto.endereco.EnderecoRequestDTO;
import br.unitins.tp1.ironforge.dto.telefone.TelefoneRequestDTO;
import br.unitins.tp1.ironforge.dto.usuario.cliente.ClienteCreateRequestDTO;
import br.unitins.tp1.ironforge.dto.usuario.cliente.ClienteUpdateRequestDTO;
import br.unitins.tp1.ironforge.infra.exception.NotFoundException;
import br.unitins.tp1.ironforge.model.Endereco;
import br.unitins.tp1.ironforge.model.Telefone;
import br.unitins.tp1.ironforge.model.usuario.Cliente;
import br.unitins.tp1.ironforge.model.usuario.Usuario;
import br.unitins.tp1.ironforge.repository.ClienteRepository;
import br.unitins.tp1.ironforge.service.cidade.CidadeService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ClienteServiceImpl implements ClienteService {

    @Inject
    public ClienteRepository clienteRepository;

    @Inject
    public UsuarioService usuarioService;

    @Inject
    public CidadeService cidadeService;

    @Override
    public Cliente findById(Long id) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null)
            throw new NotFoundException("Cliente não encontrado.");
        return cliente;
    }

    @Override
    public List<Cliente> findByNome(String nome) {
        List<Usuario> usuarios = usuarioService.findClienteByNome(nome);
        return usuarios.stream().map(u -> valueOf(u)).toList();
    }

    @Override
    public List<Cliente> findAll() {
        return clienteRepository.listAll();
    }

    @Override
    @Transactional
    public Cliente create(ClienteCreateRequestDTO dto) {
        usuarioService.validateCredentials(dto.usuario().cpf(), dto.usuario().email());
        Cliente cliente = toEntity(dto);
        usuarioService.create(cliente.getUsuario());
        clienteRepository.persist(cliente);
        return cliente;
    }

    @Override
    @Transactional
    public void update(Long id, ClienteUpdateRequestDTO dto) {
        usuarioService.validateCredentials(dto.usuario().cpf(), dto.usuario().email());

        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null)
            throw new NotFoundException("Cliente não encontrado.");
        Usuario usuario = cliente.getUsuario();
        usuario.setNome(dto.usuario().nome());
        usuario.setCpf(dto.usuario().cpf());
        usuario.setEmail(dto.usuario().email());
        usuario.setSenha(dto.usuario().senha());
        usuario.setDataNascimento(dto.usuario().dataNascimento());

        cliente.setUsuario(usuario);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Cliente cliente = clienteRepository.findById(id);
        if(cliente == null)
            throw new NotFoundException("Cliente não encontrado.");
        clienteRepository.delete(cliente);
    }

    private Cliente valueOf(Usuario usuario) {
        Cliente c = new Cliente();
        c.setId(usuario.getId());
        c.setUsuario(usuario);
        return c;
    }

    private Cliente toEntity(ClienteCreateRequestDTO dto) {
        Usuario usuario = new Usuario();
        Cliente cliente = new Cliente();
        usuario.setNome(dto.usuario().nome());
        usuario.setCpf(dto.usuario().cpf());
        usuario.setEmail(dto.usuario().email());
        usuario.setSenha(dto.usuario().senha());
        usuario.setTelefones(usuarioService.getTelefones(dto.usuario()));
        usuario.setEnderecos(usuarioService.getEnderecos(dto.usuario()));
        usuario.setDataNascimento(dto.usuario().dataNascimento());

        cliente.setUsuario(usuario);
        return cliente;

    }

    @Override
    @Transactional
    public void updateTelefone(Long id, Long idTelefone, TelefoneRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null)
            throw new NotFoundException("Cliente não encontrado.");
        Telefone telefone = cliente.getUsuario().getTelefones().stream().filter(t -> t.getId().equals(idTelefone))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Telefone não encontrado"));
        telefone.setCodigoArea(dto.codigoArea());
        telefone.setNumero(dto.numero());
    }

    @Override
    @Transactional
    public void updateEndereco(Long id, Long idEndereco, EnderecoRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null)
            throw new NotFoundException("Cliente não encontrado.");
        Endereco endereco = cliente.getUsuario().getEnderecos().stream().filter(e -> e.getId().equals(idEndereco))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Endereco não encontrado"));

        endereco.setBairro(dto.bairro());
        endereco.setCep(dto.cep());
        endereco.setComplemento(dto.complemento());
        endereco.setLogradouro(dto.logradouro());
        endereco.setNumero(dto.numero());
        endereco.setCidade(cidadeService.findById(dto.idCidade()));
    }

}

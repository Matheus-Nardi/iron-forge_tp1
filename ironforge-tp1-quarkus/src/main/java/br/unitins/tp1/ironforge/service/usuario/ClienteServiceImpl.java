package br.unitins.tp1.ironforge.service.usuario;

import java.util.List;

import br.unitins.tp1.ironforge.dto.usuario.cliente.ClienteRequestDTO;
import br.unitins.tp1.ironforge.model.usuario.Cliente;
import br.unitins.tp1.ironforge.model.usuario.Usuario;
import br.unitins.tp1.ironforge.repository.ClienteRepository;
import br.unitins.tp1.ironforge.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ClienteServiceImpl implements ClienteService {

    @Inject
    public ClienteRepository clienteRepository;
    @Inject
    public UsuarioRepository usuarioRepository;

    @Override
    public Cliente findById(Long id) {
        return clienteRepository.findById(id);
    }

    @Override
    public List<Cliente> findByNome(String nome) {
        List<Usuario> usuarios = usuarioRepository.findClienteByNome(nome);
        return usuarios.stream().map(u -> valueOf(u)).toList();
    }

    @Override
    public List<Cliente> findAll() {
        return clienteRepository.listAll();
    }

    @Override
    @Transactional
    public Cliente create(ClienteRequestDTO dto) {
        validateCredentials(dto);
        Cliente cliente = ClienteRequestDTO.toEntity(dto);
        usuarioRepository.persist(cliente.getUsuario());
        clienteRepository.persist(cliente);
        return cliente;
    }

    @Override
    @Transactional
    public void update(Long id, ClienteRequestDTO dto) {
        validateCredentials(dto);
        Cliente cliente = clienteRepository.findById(id);
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
        clienteRepository.deleteById(id);
    }

    private Cliente valueOf(Usuario usuario) {
        Cliente c = new Cliente();
        c.setId(usuario.getId());
        c.setUsuario(usuario);
        return c;
    }

    private void validateCredentials(ClienteRequestDTO cliente) {
        if (usuarioRepository.existByCpf(cliente.usuario().cpf()))
            throw new IllegalArgumentException("Já existe um usuário cadastrado com esse CPF");

        if (usuarioRepository.existByEmail(cliente.usuario().email()))
            throw new IllegalArgumentException("Já existe um usuário cadastrado com esse email");
    }

}

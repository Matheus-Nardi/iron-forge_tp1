package br.unitins.tp1.ironforge.service.usuario;

import java.util.List;

import br.unitins.tp1.ironforge.dto.usuario.UsuarioRequestDTO;
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
    public Cliente create(UsuarioRequestDTO dto) {
        validateCredentials(dto);
        Cliente cliente = UsuarioRequestDTO.toCliente(dto);
        usuarioRepository.persist(cliente.getUsuario());
        clienteRepository.persist(cliente);
        return cliente;
    }

    @Override
    @Transactional
    public void update(Long id, UsuarioRequestDTO dto) {
        validateCredentials(dto);
        Cliente cliente = clienteRepository.findById(id);
        Usuario usuario = cliente.getUsuario();
        usuario.setNome(dto.nome());
        usuario.setCpf(dto.cpf());
        usuario.setEmail(dto.email());
        usuario.setSenha(dto.senha());
        usuario.setDataNascimento(dto.dataNascimento());

        cliente.setUsuario(usuario);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        clienteRepository.deleteById(id);
    }

    private Cliente valueOf(Usuario usuario) {
        Cliente c = new Cliente();
        c.setUsuario(usuario);
        return c;
    }

    private void validateCredentials(UsuarioRequestDTO usuario) {
        if (usuarioRepository.existByCpf(usuario.cpf()))
            throw new IllegalArgumentException("Já existe um usuário cadastrado com esse CPF");

        if (usuarioRepository.existByEmail(usuario.email()))
            throw new IllegalArgumentException("Já existe um usuário cadastrado com esse email");
    }

}

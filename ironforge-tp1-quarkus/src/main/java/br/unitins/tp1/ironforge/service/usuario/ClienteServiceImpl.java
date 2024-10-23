package br.unitins.tp1.ironforge.service.usuario;

import java.util.ArrayList;
import java.util.List;

import br.unitins.tp1.ironforge.dto.endereco.EnderecoRequestDTO;
import br.unitins.tp1.ironforge.dto.telefone.TelefoneRequestDTO;
import br.unitins.tp1.ironforge.dto.usuario.cliente.ClienteCreateRequestDTO;
import br.unitins.tp1.ironforge.dto.usuario.cliente.ClienteUpdateRequestDTO;
import br.unitins.tp1.ironforge.model.Endereco;
import br.unitins.tp1.ironforge.model.Telefone;
import br.unitins.tp1.ironforge.model.usuario.Cliente;
import br.unitins.tp1.ironforge.model.usuario.Usuario;
import br.unitins.tp1.ironforge.repository.ClienteRepository;
import br.unitins.tp1.ironforge.service.cidade.CidadeService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
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
        return clienteRepository.findById(id);
    }

    @Override
    public List<Cliente> findByNome(String nome) {
        List<Usuario> usuarios = usuarioService.findClienteByNome(nome);
        List<Cliente> clientes = new ArrayList<>();

        valueOf(usuarios, clientes);

        return clientes;
    }

    private void valueOf(List<Usuario> usuarios, List<Cliente> clientes) {
        for (Usuario usuario : usuarios) {
            Cliente cliente = new Cliente();
            cliente.setId(clienteRepository.findClienteByUsuario(usuario.getId()).getId());
            cliente.setUsuario(usuario);
            clientes.add(cliente);
        }
    }

    @Override
    public List<Cliente> findAll() {
        return clienteRepository.listAll();
    }

    @Override
    @Transactional
    public Cliente create(ClienteCreateRequestDTO dto) {
        usuarioService.validateCredentials(dto.usuario().cpf(), dto.usuario().email());

        Usuario usuario = usuarioService.create(dto.usuario());
        Cliente cliente = new Cliente();
        cliente.setUsuario(usuario);
        clienteRepository.persist(cliente);
        return cliente;
    }

    @Override
    @Transactional
    public void update(Long id, ClienteUpdateRequestDTO dto) {
        usuarioService.validateCredentials(dto.usuario().cpf(), dto.usuario().email());

        Cliente cliente = clienteRepository.findById(id);
        Long idUsuario = cliente.getUsuario().getId();
        usuarioService.update(idUsuario, dto.usuario());
        cliente.setUsuario(usuarioService.findById(idUsuario));
    }

    @Override
    @Transactional
    public void delete(Long clienteId) {

        Cliente cliente = clienteRepository.findById(clienteId);
        if (cliente == null) {
            throw new EntityNotFoundException("Cliente não encontrado para o ID: " + clienteId);
        }

        Long usuarioId = cliente.getUsuario().getId();
        clienteRepository.delete(cliente);
        usuarioService.delete(usuarioId);
    }

    @Override
    @Transactional
    public void updateTelefone(Long id, Long idTelefone, TelefoneRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(id);
        Telefone telefone = cliente.getUsuario().getTelefones().stream().filter(t -> t.getId().equals(idTelefone))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Telefone não encontrado"));
        telefone.setCodigoArea(dto.codigoArea());
        telefone.setNumero(dto.numero());
    }

    @Override
    @Transactional
    public void updateEndereco(Long id, Long idEndereco, EnderecoRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(id);
        Endereco endereco = cliente.getUsuario().getEnderecos().stream().filter(e -> e.getId().equals(idEndereco))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Endereco não encontrado"));

        endereco.setBairro(dto.bairro());
        endereco.setCep(dto.cep());
        endereco.setComplemento(dto.complemento());
        endereco.setLogradouro(dto.logradouro());
        endereco.setNumero(dto.numero());
        endereco.setCidade(cidadeService.findById(dto.idCidade()));

        cliente.getUsuario().setEnderecos(cliente.getUsuario().getEnderecos());

    }

}

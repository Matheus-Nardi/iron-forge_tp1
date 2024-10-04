package br.unitins.tp1.ironforge.service.usuario;

import java.util.List;

import br.unitins.tp1.ironforge.dto.usuario.UsuarioRequestDTO;
import br.unitins.tp1.ironforge.model.usuario.Cliente;
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
        return null;
    }

    @Override
    public List<Cliente> findByNome(String nome) {
        return null;
    }

    @Override
    public List<Cliente> findAll() {
        return clienteRepository.listAll();
    }

    @Override
    @Transactional
    public Cliente create(UsuarioRequestDTO dto) {
        Cliente cliente = UsuarioRequestDTO.toCliente(dto);
        usuarioRepository.persist(cliente.getUsuario());
        clienteRepository.persist(cliente);
        return cliente;
    }

    @Override
    @Transactional
    public void update(Long id, UsuarioRequestDTO dto) {
        
    }

    @Override
    @Transactional
    public void delete(Long id) {
        
    }

}

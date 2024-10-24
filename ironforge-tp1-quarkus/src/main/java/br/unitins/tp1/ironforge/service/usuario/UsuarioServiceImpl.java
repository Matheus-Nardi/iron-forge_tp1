package br.unitins.tp1.ironforge.service.usuario;

import java.util.ArrayList;
import java.util.List;

import br.unitins.tp1.ironforge.dto.endereco.EnderecoRequestDTO;
import br.unitins.tp1.ironforge.dto.telefone.TelefoneRequestDTO;
import br.unitins.tp1.ironforge.dto.usuario.UsuarioCreateRequestDTO;
import br.unitins.tp1.ironforge.dto.usuario.UsuarioUpdateRequestDTO;
import br.unitins.tp1.ironforge.model.Endereco;
import br.unitins.tp1.ironforge.model.Telefone;
import br.unitins.tp1.ironforge.model.usuario.Usuario;
import br.unitins.tp1.ironforge.repository.UsuarioRepository;
import br.unitins.tp1.ironforge.service.cidade.CidadeService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    public UsuarioRepository usuarioRepository;

    @Inject
    public CidadeService cidadeService;

    @Override
    public void validateCredentials(String cpf, String email) {

        if (usuarioRepository.existByCpf(cpf))
            throw new IllegalArgumentException("Já existe um usuário cadastrado com esse CPF");

        if (usuarioRepository.existByEmail(email))
            throw new IllegalArgumentException("Já existe um usuário cadastrado com esse email");
    }

    @Override
    public List<Endereco> getEnderecos(UsuarioCreateRequestDTO dto) {
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
    public List<Telefone> getTelefones(UsuarioCreateRequestDTO dto) {
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

    @Override
    @Transactional
    public Usuario create(UsuarioCreateRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setCpf(dto.cpf());
        usuario.setEmail(dto.email());
        usuario.setSenha(dto.senha());
        usuario.setTelefones(dto.telefones().stream().map(TelefoneRequestDTO::convert).toList());
        usuario.setEnderecos(dto.enderecos().stream().map(e -> convertToEndereco(e)).toList());
        usuario.setDataNascimento(dto.dataNascimento());
        usuarioRepository.persist(usuario);
        return usuario;
    }

    public Endereco convertToEndereco(EnderecoRequestDTO dto) {
        Endereco e = new Endereco();

        e.setCep(dto.cep());
        e.setLogradouro(dto.logradouro());
        e.setBairro(dto.bairro());
        e.setNumero(dto.numero());
        e.setComplemento(dto.complemento());
        e.setCidade(cidadeService.findById(dto.idCidade()));

        return e;
    }

    @Override
    public List<Usuario> findClienteByNome(String nome) {
        return usuarioRepository.findClienteByNome(nome);
    }

    @Override
    public List<Usuario> findFuncionarioByNome(String nome) {
        return usuarioRepository.findFuncionarioByNome(nome);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(Long id, UsuarioUpdateRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(id);
        usuario.setNome(dto.nome());
        usuario.setCpf(dto.cpf());
        usuario.setDataNascimento(dto.dataNascimento());
        usuario.setSenha(dto.senha());
        usuario.setEmail(dto.email());
    }

    @Override
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id);
    }

}

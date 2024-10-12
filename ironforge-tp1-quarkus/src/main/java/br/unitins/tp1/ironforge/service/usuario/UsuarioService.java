package br.unitins.tp1.ironforge.service.usuario;

import java.util.List;

import br.unitins.tp1.ironforge.dto.usuario.UsuarioRequestDTO;
import br.unitins.tp1.ironforge.model.Endereco;
import br.unitins.tp1.ironforge.model.Telefone;
import br.unitins.tp1.ironforge.model.usuario.Usuario;

public interface UsuarioService {

    public Usuario create(Usuario usuario);

    public List<Usuario> findClienteByNome(String nome);

    public List<Usuario> findFuncionarioByNome(String nome);

    public void validateCredentials(String cpf, String email);

    public List<Endereco> getEnderecos(UsuarioRequestDTO dto);

    public List<Telefone> getTelefones(UsuarioRequestDTO dto);

}

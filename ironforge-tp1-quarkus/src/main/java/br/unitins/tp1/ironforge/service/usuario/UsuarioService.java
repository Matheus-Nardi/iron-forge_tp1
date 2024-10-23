package br.unitins.tp1.ironforge.service.usuario;

import java.util.List;

import br.unitins.tp1.ironforge.dto.usuario.UsuarioCreateRequestDTO;
import br.unitins.tp1.ironforge.dto.usuario.UsuarioUpdateRequestDTO;
import br.unitins.tp1.ironforge.model.Endereco;
import br.unitins.tp1.ironforge.model.Telefone;
import br.unitins.tp1.ironforge.model.usuario.Usuario;

public interface UsuarioService {

    Usuario create(UsuarioCreateRequestDTO usuario);

    void delete(Long id);

    void update(Long id, UsuarioUpdateRequestDTO dto);

    Usuario findById(Long id);

    List<Usuario> findClienteByNome(String nome);

    List<Usuario> findFuncionarioByNome(String nome);

    void validateCredentials(String cpf, String email);

    List<Endereco> getEnderecos(UsuarioCreateRequestDTO dto);

    List<Telefone> getTelefones(UsuarioCreateRequestDTO dto);

}

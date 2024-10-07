package br.unitins.tp1.ironforge.dto.usuario.cliente;

import br.unitins.tp1.ironforge.dto.usuario.UsuarioRequestDTO;
import br.unitins.tp1.ironforge.model.usuario.Cliente;
import br.unitins.tp1.ironforge.model.usuario.Telefone;
import br.unitins.tp1.ironforge.model.usuario.Usuario;

public record ClienteRequestDTO(
        UsuarioRequestDTO usuario) {

    public static Cliente toEntity(ClienteRequestDTO dto) {
        Usuario usuario = new Usuario();
        Cliente cliente = new Cliente();
        Telefone telefone = new Telefone();

        telefone.setCodigoArea(dto.usuario.telefone().codigoArea());
        telefone.setNumero(dto.usuario.telefone().numero());

        usuario.setNome(dto.usuario.nome());
        usuario.setCpf(dto.usuario.cpf());
        usuario.setEmail(dto.usuario.email());
        usuario.setSenha(dto.usuario.senha());
        usuario.setTelefone(telefone);
        usuario.setDataNascimento(dto.usuario.dataNascimento());

        cliente.setUsuario(usuario);
        return cliente;

    }
}

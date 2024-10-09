package br.unitins.tp1.ironforge.dto.usuario.funcionario;

import java.math.BigDecimal;

import br.unitins.tp1.ironforge.dto.usuario.UsuarioRequestDTO;
import br.unitins.tp1.ironforge.model.Telefone;
import br.unitins.tp1.ironforge.model.usuario.Funcionario;
import br.unitins.tp1.ironforge.model.usuario.Usuario;

public record FuncionarioRequestDTO(

        UsuarioRequestDTO usuario,
        BigDecimal salario) {

    public static Funcionario toEntity(FuncionarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        Funcionario funcionario = new Funcionario();
        Telefone telefone = new Telefone();

        telefone.setCodigoArea(dto.usuario.telefone().codigoArea());
        telefone.setNumero(dto.usuario.telefone().numero());

        usuario.setNome(dto.usuario().nome());
        usuario.setCpf(dto.usuario().cpf());
        usuario.setEmail(dto.usuario().email());
        usuario.setSenha(dto.usuario().senha());
        usuario.setDataNascimento(dto.usuario().dataNascimento());
        usuario.setTelefone(telefone);
        funcionario.setSalario(dto.salario());
        funcionario.setUsuario(usuario);
        return funcionario;
    }
}

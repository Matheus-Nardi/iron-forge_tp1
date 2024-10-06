package br.unitins.tp1.ironforge.dto.usuario.funcionario;

import java.math.BigDecimal;

import br.unitins.tp1.ironforge.dto.usuario.UsuarioResponseDTO;
import br.unitins.tp1.ironforge.model.usuario.Funcionario;

public record FuncionarioResponseDTO(

        UsuarioResponseDTO usuario,
        BigDecimal salario) {

    public static FuncionarioResponseDTO valueOf(Funcionario funcionario) {
        return new FuncionarioResponseDTO(
                new UsuarioResponseDTO(funcionario.getId(), funcionario.getUsuario().getNome(),
                        funcionario.getUsuario().getEmail()),
                funcionario.getSalario());
    }
}

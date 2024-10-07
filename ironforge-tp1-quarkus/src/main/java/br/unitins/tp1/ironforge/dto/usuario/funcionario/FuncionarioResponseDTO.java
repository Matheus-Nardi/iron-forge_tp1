package br.unitins.tp1.ironforge.dto.usuario.funcionario;

import java.math.BigDecimal;

import br.unitins.tp1.ironforge.dto.usuario.TelefoneResponseDTO;
import br.unitins.tp1.ironforge.dto.usuario.UsuarioResponseDTO;
import br.unitins.tp1.ironforge.model.usuario.Funcionario;

public record FuncionarioResponseDTO(
                Long id,
                UsuarioResponseDTO usuario,
                BigDecimal salario) {

        public static FuncionarioResponseDTO valueOf(Funcionario funcionario) {
                return new FuncionarioResponseDTO(funcionario.getId(),
                                new UsuarioResponseDTO(funcionario.getUsuario().getId(),
                                                funcionario.getUsuario().getNome(),
                                                funcionario.getUsuario().getEmail(),
                                                TelefoneResponseDTO.valueOf(funcionario.getUsuario().getTelefone())),
                                funcionario.getSalario());
        }
}

package br.unitins.tp1.ironforge.dto.usuario.funcionario;

import java.math.BigDecimal;

import br.unitins.tp1.ironforge.dto.usuario.UsuarioRequestDTO;

public record FuncionarioRequestDTO(

        UsuarioRequestDTO usuario,
        BigDecimal salario) {

}

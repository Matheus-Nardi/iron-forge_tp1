package br.unitins.tp1.ironforge.dto.usuario.funcionario;

import java.math.BigDecimal;

import br.unitins.tp1.ironforge.dto.usuario.UsuarioCreateRequestDTO;

public record FuncionarioCreateRequestDTO(

        UsuarioCreateRequestDTO usuario,
        BigDecimal salario) {

}

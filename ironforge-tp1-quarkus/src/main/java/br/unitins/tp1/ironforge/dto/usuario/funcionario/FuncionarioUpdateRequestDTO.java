package br.unitins.tp1.ironforge.dto.usuario.funcionario;

import java.math.BigDecimal;

import br.unitins.tp1.ironforge.dto.usuario.UsuarioUpdateRequestDTO;

public record FuncionarioUpdateRequestDTO(

        UsuarioUpdateRequestDTO usuario,
        BigDecimal salario) {

}

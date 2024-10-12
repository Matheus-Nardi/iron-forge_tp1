package br.unitins.tp1.ironforge.dto.fabricante;

import java.util.List;

import org.hibernate.validator.constraints.br.CNPJ;

import br.unitins.tp1.ironforge.dto.endereco.EnderecoRequestDTO;
import br.unitins.tp1.ironforge.dto.telefone.TelefoneRequestDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record FabricanteRequestDTO(

    @NotBlank(message = "O campo nome deve ser informado.")
    String nome,
    @NotBlank(message = "O campo CNPJ deve ser informado.")
    @CNPJ
    String cnpj,
    @NotBlank(message = "O campo email deve ser informado.")
    @Email
    String email,
    List<TelefoneRequestDTO> telefones,

    List<EnderecoRequestDTO> enderecos
) {

}

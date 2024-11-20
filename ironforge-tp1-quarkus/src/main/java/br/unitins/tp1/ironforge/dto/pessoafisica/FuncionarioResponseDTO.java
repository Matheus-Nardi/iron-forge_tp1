package br.unitins.tp1.ironforge.dto.pessoafisica;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import br.unitins.tp1.ironforge.dto.endereco.EnderecoResponseDTO;
import br.unitins.tp1.ironforge.dto.telefone.TelefoneResponseDTO;
import br.unitins.tp1.ironforge.dto.usuario.UsuarioResponseDTO;
import br.unitins.tp1.ironforge.model.usuario.Funcionario;

public record FuncionarioResponseDTO(
        Long id,
        String nome,
        String cnpj,
        String email,
        LocalDate dataNascimento,
        List<TelefoneResponseDTO> telefones,
        List<EnderecoResponseDTO> enderecos,
        UsuarioResponseDTO usuario,
        BigDecimal salario,
        String cargo,
        LocalDate dataContracao,
        String fotoPerfil) {
    public static FuncionarioResponseDTO valueOf(Funcionario funcionario) {
        return new FuncionarioResponseDTO(funcionario.getId(), funcionario.getPessoaFisica().getNome(),
                funcionario.getPessoaFisica().getCpf(),
                funcionario.getPessoaFisica().getEmail(),
                funcionario.getPessoaFisica().getDataNascimento(),
                funcionario.getPessoaFisica().getTelefones().stream().map(TelefoneResponseDTO::valueOf).toList(),
                funcionario.getPessoaFisica().getEnderecos().stream().map(EnderecoResponseDTO::valueOf).toList(),
                UsuarioResponseDTO.valueOf(funcionario.getPessoaFisica().getUsuario()),
                funcionario.getSalario(),
                funcionario.getCargo(),
                funcionario.getDataContratacao(),
                funcionario.getPessoaFisica().getFotoPerfil());
    }
}

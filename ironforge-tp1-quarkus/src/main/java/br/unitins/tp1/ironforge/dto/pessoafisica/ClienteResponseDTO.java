package br.unitins.tp1.ironforge.dto.pessoafisica;

import java.time.LocalDate;
import java.util.List;

import br.unitins.tp1.ironforge.dto.endereco.EnderecoResponseDTO;
import br.unitins.tp1.ironforge.dto.telefone.TelefoneResponseDTO;
import br.unitins.tp1.ironforge.dto.usuario.UsuarioResponseDTO;
import br.unitins.tp1.ironforge.model.usuario.Cliente;

public record ClienteResponseDTO(
        Long id,
        String nome,
        String cnpj,
        String email,
        LocalDate dataNascimento,
        List<TelefoneResponseDTO> telefones,
        List<EnderecoResponseDTO> enderecos,
        UsuarioResponseDTO usuario,
        String fotoPerfil) {
    public static ClienteResponseDTO valueOf(Cliente cliente) {
        return new ClienteResponseDTO(cliente.getId(), cliente.getPessoaFisica().getNome(),
                cliente.getPessoaFisica().getCpf(),
                cliente.getPessoaFisica().getEmail(),
                cliente.getPessoaFisica().getDataNascimento(),
                cliente.getPessoaFisica().getTelefones().stream().map(TelefoneResponseDTO::valueOf).toList(),
                cliente.getPessoaFisica().getEnderecos().stream().map(EnderecoResponseDTO::valueOf).toList(),
                UsuarioResponseDTO.valueOf(cliente.getPessoaFisica().getUsuario()),
                cliente.getPessoaFisica().getFotoPerfil());
    }
}

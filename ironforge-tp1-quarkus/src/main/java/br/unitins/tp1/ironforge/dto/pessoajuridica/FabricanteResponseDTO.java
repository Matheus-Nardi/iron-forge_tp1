package br.unitins.tp1.ironforge.dto.pessoajuridica;

import java.util.List;

import br.unitins.tp1.ironforge.dto.endereco.EnderecoResponseDTO;
import br.unitins.tp1.ironforge.dto.telefone.TelefoneResponseDTO;
import br.unitins.tp1.ironforge.dto.usuario.UsuarioResponseDTO;
import br.unitins.tp1.ironforge.model.Fabricante;

public record FabricanteResponseDTO(
        Long id,
        String nome,
        String cnpj,
        String email,
        List<TelefoneResponseDTO> telefones,
        List<EnderecoResponseDTO> enderecos,
        UsuarioResponseDTO usuario) {
    public static FabricanteResponseDTO valueOf(Fabricante fabricante) {
        return new FabricanteResponseDTO(fabricante.getId(), fabricante.getPessoaJuridica().getNome(),
                fabricante.getPessoaJuridica().getCnpj(),
                fabricante.getPessoaJuridica().getEmail(),
                fabricante.getPessoaJuridica().getTelefones().stream().map(TelefoneResponseDTO::valueOf).toList(),
                fabricante.getPessoaJuridica().getEnderecos().stream().map(EnderecoResponseDTO::valueOf).toList(),
                UsuarioResponseDTO.valueOf(fabricante.getPessoaJuridica().getUsuario()));
    }
}

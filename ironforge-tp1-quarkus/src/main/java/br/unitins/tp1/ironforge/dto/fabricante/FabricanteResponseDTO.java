package br.unitins.tp1.ironforge.dto.fabricante;

import java.util.List;

import br.unitins.tp1.ironforge.dto.endereco.EnderecoResponseDTO;
import br.unitins.tp1.ironforge.dto.telefone.TelefoneResponseDTO;
import br.unitins.tp1.ironforge.model.Fabricante;

public record FabricanteResponseDTO(
        Long id,
        String nome,
        String cnpj,
        String email,
        List<TelefoneResponseDTO> telefones,
        List<EnderecoResponseDTO> enderecos) {
    public static FabricanteResponseDTO valueOf(Fabricante fabricante) {
        return new FabricanteResponseDTO(fabricante.getId(), fabricante.getNome(), fabricante.getCnpj(),
                fabricante.getEmail(), fabricante.getTelefones().stream().map(TelefoneResponseDTO::valueOf).toList(),
                fabricante.getEnderecos().stream().map(EnderecoResponseDTO::valueOf).toList());
    }
}

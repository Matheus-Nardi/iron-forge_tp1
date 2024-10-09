package br.unitins.tp1.ironforge.dto.fabricante;

import br.unitins.tp1.ironforge.dto.usuario.TelefoneResponseDTO;
import br.unitins.tp1.ironforge.model.Fabricante;

public record FabricanteResponseDTO(
        Long id,
        String nome,
        String cnpj,
        String email,
        TelefoneResponseDTO telefone) {
    public static FabricanteResponseDTO valueOf(Fabricante fabricante) {
        return new FabricanteResponseDTO(fabricante.getId(), fabricante.getNome(), fabricante.getCnpj(),
                fabricante.getEmail(), TelefoneResponseDTO.valueOf(fabricante.getTelefone()));
    }
}

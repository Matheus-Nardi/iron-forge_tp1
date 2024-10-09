package br.unitins.tp1.ironforge.dto.usuario;

import br.unitins.tp1.ironforge.model.Telefone;

public record TelefoneResponseDTO(
        Long id,
        String codigoArea,
        String numero) {

    public static TelefoneResponseDTO valueOf(Telefone telefone) {
        return new TelefoneResponseDTO(telefone.getId(), telefone.getCodigoArea(), telefone.getNumero());
    }

}

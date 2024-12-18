package br.unitins.tp1.ironforge.dto.telefone;

import br.unitins.tp1.ironforge.model.usuario.Telefone;

public record TelefoneResponseDTO(
        Long id,
        String codigoArea,
        String numero) {

    public static TelefoneResponseDTO valueOf(Telefone telefone) {
        return new TelefoneResponseDTO(telefone.getId(), telefone.getCodigoArea(), telefone.getNumero());
    }

}

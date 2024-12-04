package br.unitins.tp1.ironforge.dto.cidade;

import br.unitins.tp1.ironforge.dto.estado.EstadoResponseDTO;
import br.unitins.tp1.ironforge.model.endereco.Cidade;

public record CidadeResponseDTO(
        Long id,
        String nome,
        EstadoResponseDTO estado) {

    public static CidadeResponseDTO valueOf(Cidade cidade) {
        return new CidadeResponseDTO(cidade.getId(), cidade.getNome(), EstadoResponseDTO.valueOf(cidade.getEstado()));
    }

}

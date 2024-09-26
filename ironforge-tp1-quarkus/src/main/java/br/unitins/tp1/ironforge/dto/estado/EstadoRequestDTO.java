package br.unitins.tp1.ironforge.dto.estado;

import br.unitins.tp1.ironforge.model.Estado;

public record EstadoRequestDTO(String nome, String sigla) {

    public static Estado valueOf(EstadoRequestDTO dto) {
        Estado e = new Estado();
        e.setNome(dto.nome);
        e.setSigla(dto.sigla);
        return e;
    }
}

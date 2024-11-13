package br.unitins.tp1.ironforge.dto.avaliacao;

import br.unitins.tp1.ironforge.dto.whey.WheyProteinResponseDTO;
import br.unitins.tp1.ironforge.model.avaliacao.Avaliacao;
import br.unitins.tp1.ironforge.model.avaliacao.Nota;

public record AvaliacaoResponseDTO(
        Long id,
        String comentario,
        Nota nota,
        WheyProteinResponseDTO whey,
        String usuario) {

    public static AvaliacaoResponseDTO valueOf(Avaliacao avaliacao) {
        return new AvaliacaoResponseDTO(avaliacao.getId(), avaliacao.getComentario(), avaliacao.getNota(),
                WheyProteinResponseDTO.valueOf(avaliacao.getWheyProtein()),
                avaliacao.getCliente().getPessoaFisica().getUsuario().getUsername());
    }
}

package br.unitins.tp1.ironforge.dto.avaliacao;

import br.unitins.tp1.ironforge.model.avaliacao.Avaliacao;

public record AvaliacaoResponseDTO(
        Long id,
        String comentario,
        Integer nota,
        String whey,
        String usuario) {

    public static AvaliacaoResponseDTO valueOf(Avaliacao avaliacao) {
        return new AvaliacaoResponseDTO(avaliacao.getId(), avaliacao.getComentario(), avaliacao.getNota().getId(),
                avaliacao.getWheyProtein().getNome(),
                avaliacao.getCliente().getPessoaFisica().getUsuario().getUsername());
    }
}

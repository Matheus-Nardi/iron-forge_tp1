package br.unitins.tp1.ironforge.dto.cupom;

import java.time.LocalDateTime;

import br.unitins.tp1.ironforge.model.Cupom;

public record CupomResponseDTO(
        Long id,
        String fabricante,
        String codigo,
        Double percentualDesconto,
        LocalDateTime dataValidade,
        Boolean ativo) {

    public static CupomResponseDTO valueOf(Cupom cupom) {
        return new CupomResponseDTO(cupom.getId(), cupom.getFabricante().getPessoaJuridica().getNome(),
                cupom.getCodigo(),
                cupom.getPercentualDesconto(),
                cupom.getDataValidade(), cupom.getAtivo());
    }
}

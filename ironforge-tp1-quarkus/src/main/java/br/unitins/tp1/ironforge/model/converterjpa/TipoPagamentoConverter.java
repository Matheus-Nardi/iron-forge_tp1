package br.unitins.tp1.ironforge.model.converterjpa;

import br.unitins.tp1.ironforge.model.pagamento.TipoPagamento;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipoPagamentoConverter implements AttributeConverter<TipoPagamento, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoPagamento tipoPagamento) {
        return tipoPagamento.equals(null) ? null : tipoPagamento.getId();
    }

    @Override
    public TipoPagamento convertToEntityAttribute(Integer id) {
        return TipoPagamento.valueOf(id);
    }

}

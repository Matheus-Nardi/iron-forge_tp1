package br.unitins.tp1.ironforge.model.converterjpa;

import br.unitins.tp1.ironforge.model.pedido.Situacao;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SituacaoConverter implements AttributeConverter<Situacao,Integer> {

    @Override
    public Integer convertToDatabaseColumn(Situacao situacao) {
        return situacao.equals(null) ? null : situacao.getId();
    }

    @Override
    public Situacao convertToEntityAttribute(Integer id) {
       return Situacao.valueOf(id);
    }

}

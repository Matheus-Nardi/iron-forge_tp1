package br.unitins.tp1.ironforge.model.converterjpa;

import br.unitins.tp1.ironforge.model.pagamento.TipoCartao;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipoCartaoConverter implements AttributeConverter<TipoCartao,Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoCartao tipoCartao) {
        return tipoCartao.equals(null) ? null : tipoCartao.getId();
    }

    @Override
    public TipoCartao convertToEntityAttribute(Integer id) {
       return TipoCartao.valueOf(id);
    }

}

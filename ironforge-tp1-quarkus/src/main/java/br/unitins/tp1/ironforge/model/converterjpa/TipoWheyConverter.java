package br.unitins.tp1.ironforge.model.converterjpa;

import br.unitins.tp1.ironforge.model.whey.TipoWhey;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipoWheyConverter implements AttributeConverter<TipoWhey,Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoWhey tipoWhey) {
        return tipoWhey.equals(null) ? null : tipoWhey.getId();
    }

    @Override
    public TipoWhey convertToEntityAttribute(Integer id) {
       return TipoWhey.valueOf(id);
    }

}

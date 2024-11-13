package br.unitins.tp1.ironforge.model.converterjpa;

import br.unitins.tp1.ironforge.model.avaliacao.Nota;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class NotaConverter implements AttributeConverter<Nota, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Nota nota) {
        return nota.equals(null) ? null : nota.getId();
    }

    @Override
    public Nota convertToEntityAttribute(Integer id) {
        return Nota.valueOf(id);
    }

}

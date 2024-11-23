package br.unitins.tp1.ironforge.model.whey;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import br.unitins.tp1.ironforge.validation.EntidadeNotFoundException;

@JsonFormat(shape = Shape.OBJECT)
public enum TipoWhey {

    CONCENTRADO(1, "Concentrado"), ISOLADO(2, "Isolado"), HIDROLISADO(3, "Hidrolisado");

    private final Integer id;
    private final String label;

    private TipoWhey(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

    public Integer getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static TipoWhey valueOf(Integer id) {
        if (id.equals(null))
            return null;
        for (TipoWhey tipo : values()) {
            if (tipo.getId().equals(id))
                return tipo;
        }

        throw new EntidadeNotFoundException("idTipoWhey", "Tipo de whey não encontrado!");
    }

}

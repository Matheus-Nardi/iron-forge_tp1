package br.unitins.tp1.ironforge.model.avaliacao;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import br.unitins.tp1.ironforge.validation.EntidadeNotFoundException;

@JsonFormat(shape = Shape.OBJECT)
public enum Nota {

    ESTRELA_1(1, "Estrela 1"),
    ESTRELA_2(2, "Estrela 2"),
    ESTRELA_3(3, "Estrela 3"),
    ESTRELA_4(4, "Estrela 4"),
    ESTRELA_5(5, "Estrela 5");

    private final Integer id;
    private final String label;

    private Nota(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

    public Integer getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static Nota valueOf(Integer id) {
        if (id.equals(null))
            return null;
        for (Nota tipo : values()) {
            if (tipo.getId().equals(id))
                return tipo;
        }

        throw new EntidadeNotFoundException("nota", "Tipo de estrela não encontrado!");
    }

}

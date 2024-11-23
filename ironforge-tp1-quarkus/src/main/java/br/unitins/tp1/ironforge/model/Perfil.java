package br.unitins.tp1.ironforge.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import br.unitins.tp1.ironforge.validation.EntidadeNotFoundException;

@JsonFormat(shape = Shape.OBJECT)
public enum Perfil {

    ADM(1, "Adm"), USER(2, "User");

    private final Integer id;
    private final String label;

    private Perfil(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

    public Integer getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static Perfil valueOf(Integer id) {
        if (id.equals(null))
            return null;
        for (Perfil tipo : values()) {
            if (tipo.getId().equals(id))
                return tipo;
        }

        throw new EntidadeNotFoundException("perfil","Tipo de whey não encontrado!");
    }

}

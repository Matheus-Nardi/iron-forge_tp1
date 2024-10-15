package br.unitins.tp1.ironforge.model.whey.tabelanutricional;

import br.unitins.tp1.ironforge.model.DefaultEntity;
import jakarta.persistence.Entity;

@Entity
public class FullNutrient extends DefaultEntity {
    public int attr_id;
    public int value;

    public FullNutrient() {
        super();
    }

    public int getAttr_id() {
        return attr_id;
    }

    public void setAttr_id(int attr_id) {
        this.attr_id = attr_id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
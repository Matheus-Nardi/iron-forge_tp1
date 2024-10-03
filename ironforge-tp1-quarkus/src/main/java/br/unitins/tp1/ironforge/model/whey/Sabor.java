package br.unitins.tp1.ironforge.model.whey;

import br.unitins.tp1.ironforge.model.DefaultEntity;
import jakarta.persistence.Entity;

@Entity
public class Sabor extends DefaultEntity {

    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}

package br.unitins.tp1.ironforge.model.usuario;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import br.unitins.tp1.ironforge.validation.EntidadeNotFoundException;

@JsonFormat(shape = Shape.OBJECT)
public enum Perfil {

    FUNCIONARIO(1, "Funcionario"), CLIENTE(2, "Cliente"), ADMINISTRADOR(3, "Administrador"),
    FABRICANTE(4, "Fabricante"), USUARIO(5, "Usuario");

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
        for (Perfil tipo : values()) {
            if (tipo.getId() == id)
                return tipo;
        }

        throw new EntidadeNotFoundException("perfil", "Tipo de perfil não encontrado!");
    }

}

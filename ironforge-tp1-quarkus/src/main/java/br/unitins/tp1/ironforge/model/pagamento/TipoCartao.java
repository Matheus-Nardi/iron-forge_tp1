package br.unitins.tp1.ironforge.model.pagamento;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@JsonFormat(shape = Shape.OBJECT)
public enum TipoCartao {

    DEBITO(1, "Débito"), CREDITO(2, "Crédito");

    private final Integer id;
    private final String label;

    private TipoCartao(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

    public Integer getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static TipoCartao valueOf(Integer id) {
        if (id.equals(null))
            return null;
        for (TipoCartao tipo : values()) {
            if (tipo.getId().equals(id))
                return tipo;
        }

        throw new IllegalArgumentException("Tipo de cartão não encontrado!");
    }

}

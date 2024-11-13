package br.unitins.tp1.ironforge.model.pagamento;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@JsonFormat(shape = Shape.OBJECT)
public enum StatusPagamento {

    PENDENTE(1, "Pendente"), PAGO(1, "Pago"), VENCIDO(1, "Vencido");
    private final Integer id;
    private final String label;

    private StatusPagamento(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

    public Integer getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static StatusPagamento valueOf(Integer id) {
        if (id.equals(null))
            return null;
        for (StatusPagamento tipo : values()) {
            if (tipo.getId().equals(id))
                return tipo;
        }

        throw new IllegalArgumentException("Tipo de situação não encontrado!");
    }

}

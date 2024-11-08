package br.unitins.tp1.ironforge.model.pedido;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@JsonFormat(shape = Shape.OBJECT)
public enum Situacao {

    SEPARANDO_PEDIDO(1, "O pedido está sendo separado"),
    PRONTO_PARA_ENVIO(2, "Pedido pronto para envio"),             
    ENVIADO(3, "Pedido enviado, aguardando atualização de trânsito"), 
    EM_TRANSITO(4, "Pedido em trânsito"),
    ENTREGUE(5, "Pedido entregue"),
    DESISTIDO(6, "Desistência do pedido antes do envio"),
    DEVOLVIDO(7, "Devolução do pedido após a entrega");

    private final Integer id;
    private final String label;

    private Situacao(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

    public Integer getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static Situacao valueOf(Integer id) {
        if (id.equals(null))
            return null;
        for (Situacao tipo : values()) {
            if (tipo.getId().equals(id))
                return tipo;
        }

        throw new IllegalArgumentException("Tipo de situação não encontrado!");
    }

}
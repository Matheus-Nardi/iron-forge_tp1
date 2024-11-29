package br.unitins.tp1.ironforge.model.pedido;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import br.unitins.tp1.ironforge.validation.EntidadeNotFoundException;

@JsonFormat(shape = Shape.OBJECT)
public enum Situacao {

    SEPARANDO_PEDIDO(1, "O pedido está sendo separado"),
    PRONTO_PARA_ENVIO(2, "Pedido pronto para envio"),
    ENVIADO(3, "Pedido enviado, aguardando atualização de trânsito"),
    EM_TRANSITO(4, "Pedido em trânsito"),
    ENTREGUE(5, "Pedido entregue"),
    CANCELADO(6, "Pedido cancelado"),
    DEVOLVIDO(7, "Devolução do pedido após a entrega"),
    AGURARDANDO_PAGAMENTO(8, "Aguardando o pagamento do pedido");

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

        throw new EntidadeNotFoundException("idSituacao", "Tipo de situação não encontrado!");
    }

}

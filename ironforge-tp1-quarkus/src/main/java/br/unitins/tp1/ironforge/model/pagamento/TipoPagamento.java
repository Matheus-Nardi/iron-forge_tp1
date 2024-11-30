package br.unitins.tp1.ironforge.model.pagamento;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import br.unitins.tp1.ironforge.validation.EntidadeNotFoundException;

@JsonFormat(shape = Shape.OBJECT)
public enum TipoPagamento {

    PIX(1, "Pix"), BOLETO(2, "Boleto"), CARTAO(3, "Cartão"), NAO_REALIZADO(4, "Não realizado");

    private final Integer id;
    private final String label;

    private TipoPagamento(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

    public Integer getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static TipoPagamento valueOf(Integer id) {
        if (id.equals(null))
            return null;
        for (TipoPagamento tipo : values()) {
            if (tipo.getId().equals(id))
                return tipo;
        }

        throw new EntidadeNotFoundException("tipoPagamento", "Tipo de pagameto não encontrado!");
    }   

}

package br.unitins.tp1.ironforge.model.pagamento;

import jakarta.persistence.Entity;

@Entity
public class Boleto extends Pagamento {

    private String codigoBarras;

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

}

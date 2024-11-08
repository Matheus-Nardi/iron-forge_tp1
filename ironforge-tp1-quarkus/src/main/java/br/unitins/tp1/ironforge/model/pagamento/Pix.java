package br.unitins.tp1.ironforge.model.pagamento;

import jakarta.persistence.Entity;

@Entity
public class Pix extends Pagamento {

    private String destinatario;
    private String chave;

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

}

package br.unitins.tp1.ironforge.model.usuario;

import br.unitins.tp1.ironforge.model.DefaultEntity;
import br.unitins.tp1.ironforge.model.PessoaFisica;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Cliente extends DefaultEntity {

    @OneToOne
    @JoinColumn(name = "id_pessoaFisica", unique = true)
    private PessoaFisica pessoaFisica;

    public PessoaFisica getPessoaFisica() {
        return pessoaFisica;
    }

    public void setPessoaFisica(PessoaFisica pessoaFisica) {
        this.pessoaFisica = pessoaFisica;
    }

}

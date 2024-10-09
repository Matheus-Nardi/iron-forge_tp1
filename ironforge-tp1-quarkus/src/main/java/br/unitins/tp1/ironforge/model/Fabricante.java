package br.unitins.tp1.ironforge.model;

import br.unitins.tp1.ironforge.model.usuario.Telefone;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Fabricante extends DefaultEntity {

    private String nome;

    private String cnpj;

    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_telefone")
    private Telefone telefone;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Telefone getTelefone() {
        return telefone;
    }

    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }

}

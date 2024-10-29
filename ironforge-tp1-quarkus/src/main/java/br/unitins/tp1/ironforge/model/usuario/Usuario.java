package br.unitins.tp1.ironforge.model.usuario;

import br.unitins.tp1.ironforge.model.DefaultEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Usuario extends DefaultEntity {

    private String username;
    @Column(length = 20)
    private String senha;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}

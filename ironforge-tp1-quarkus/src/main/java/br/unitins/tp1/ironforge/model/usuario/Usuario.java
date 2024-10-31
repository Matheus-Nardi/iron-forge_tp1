package br.unitins.tp1.ironforge.model.usuario;

import br.unitins.tp1.ironforge.model.DefaultEntity;
import br.unitins.tp1.ironforge.model.Perfil;
import jakarta.persistence.Entity;

@Entity
public class Usuario extends DefaultEntity {

    private String username;
    private String senha;

    private Perfil perfil;

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

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

}

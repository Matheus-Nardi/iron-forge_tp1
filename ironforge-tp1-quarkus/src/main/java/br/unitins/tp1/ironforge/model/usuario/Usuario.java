package br.unitins.tp1.ironforge.model.usuario;

import java.time.LocalDate;

import br.unitins.tp1.ironforge.model.DefaultEntity;
import jakarta.persistence.Entity;

@Entity
public class Usuario extends DefaultEntity {

    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private LocalDate dataNascimento;


    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    
}

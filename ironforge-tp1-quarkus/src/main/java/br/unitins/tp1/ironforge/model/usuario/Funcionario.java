package br.unitins.tp1.ironforge.model.usuario;

import java.math.BigDecimal;

import br.unitins.tp1.ironforge.model.DefaultEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Funcionario extends DefaultEntity {

    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    private BigDecimal salario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

}

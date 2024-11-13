package br.unitins.tp1.ironforge.model.avaliacao;

import java.time.LocalDate;

import br.unitins.tp1.ironforge.model.DefaultEntity;
import br.unitins.tp1.ironforge.model.usuario.Cliente;
import br.unitins.tp1.ironforge.model.whey.WheyProtein;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Avaliacao extends DefaultEntity {

    private String comentario;

    private LocalDate data;

    private Nota nota;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_whey")
    private WheyProtein wheyProtein;

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Nota getNota() {
        return nota;
    }

    public void setNota(Nota nota) {
        this.nota = nota;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public WheyProtein getWheyProtein() {
        return wheyProtein;
    }

    public void setWheyProtein(WheyProtein wheyProtein) {
        this.wheyProtein = wheyProtein;
    }

}

package br.unitins.tp1.ironforge.model.pedido;

import java.time.LocalDate;

import br.unitins.tp1.ironforge.model.DefaultEntity;
import br.unitins.tp1.ironforge.model.whey.WheyProtein;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Lote extends DefaultEntity {

    @Column(unique = true)
    private String codigo;
    private Integer quantidade;
    private LocalDate dataFabricacao;

    @ManyToOne
    @JoinColumn(name = "id_whey")
    private WheyProtein wheyProtein;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDate getDataFabricacao() {
        return dataFabricacao;
    }

    public void setDataFabricacao(LocalDate dataFabricacao) {
        this.dataFabricacao = dataFabricacao;
    }

    public WheyProtein getWheyProtein() {
        return wheyProtein;
    }

    public void setWheyProtein(WheyProtein wheyProtein) {
        this.wheyProtein = wheyProtein;
    }

}

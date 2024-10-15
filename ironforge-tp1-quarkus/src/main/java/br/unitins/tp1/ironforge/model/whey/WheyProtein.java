package br.unitins.tp1.ironforge.model.whey;

import br.unitins.tp1.ironforge.model.DefaultEntity;
import br.unitins.tp1.ironforge.model.Fabricante;
import br.unitins.tp1.ironforge.model.whey.tabelanutricional.Food;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class WheyProtein extends DefaultEntity {

    private String upc;
    private String nome;
    private String descricao;
    private Double preco;
    private Integer peso;

    private TipoWhey tipoWhey;

    @ManyToOne
    @JoinColumn(name = "id_sabor")
    private Sabor sabor;

    @ManyToOne
    @JoinColumn(name = "id_fabricante")
    private Fabricante fabricante;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_food")
    private Food food;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public Sabor getSabor() {
        return sabor;
    }

    public void setSabor(Sabor sabor) {
        this.sabor = sabor;
    }

    public Fabricante getFabricante() {
        return fabricante;
    }

    public void setFabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
    }

    public TipoWhey getTipoWhey() {
        return tipoWhey;
    }

    public void setTipoWhey(TipoWhey tipoWhey) {
        this.tipoWhey = tipoWhey;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

}

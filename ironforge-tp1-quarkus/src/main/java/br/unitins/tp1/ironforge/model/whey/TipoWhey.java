package br.unitins.tp1.ironforge.model.whey;

public enum TipoWhey {

    CONCENTRADO(1, "Concentrado"), ISOLADO(2, "Isolado"), HIDROLISADO(3, "Hidrolisado");

    public int id;
    public String label;

    private TipoWhey(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static TipoWhey valueOf(int id) {
        for (TipoWhey tipo : values()) {
            if (tipo.getId() == id)
                return tipo;
        }

        throw new IllegalArgumentException("Tipo de whey não encontrado!");
    }

}

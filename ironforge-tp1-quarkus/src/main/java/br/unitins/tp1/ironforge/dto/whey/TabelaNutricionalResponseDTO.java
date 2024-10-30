package br.unitins.tp1.ironforge.dto.whey;

import br.unitins.tp1.ironforge.model.whey.tabelanutricional.Food;

public record TabelaNutricionalResponseDTO(
        int quantidadePorcao,
        String unidadePorcao,
        int pesoPorcaoGramas,
        int proteinas,
        int carboidratos,
        int calorias,
        int gorduras,
        String gordurasSaturadas,
        int sodio,
        String fibras,
        String acucares,
        String ingredientes) {

    public static TabelaNutricionalResponseDTO valeuOf(Food food) {
        return new TabelaNutricionalResponseDTO(food.getServing_qty(), food.getServing_unit(),
                food.getServing_weight_grams(),
                food.getNf_protein(),
                food.getNf_total_carbohydrate(), food.getNf_calories(),
                food.getNf_total_fat(),
                food.getNf_saturated_fat(), food.getNf_sodium(), food.getNf_dietary_fiber(), food.getNf_sugars(),
                food.getNf_ingredient_statement());
    }
}

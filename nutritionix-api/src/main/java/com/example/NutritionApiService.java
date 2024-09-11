package com.example;

import com.example.models.Root;
import com.example.web.NutritionApiConsumer;

public class NutritionApiService {

    public static void main(String[] args) {
        NutritionApiConsumer consumer = new NutritionApiConsumer();

        Root root = consumer.getFoodInfoByUPC("749826177748");

        if (root != null && root.getFoods() != null) {
            root.getFoods().forEach(System.out::println);
        } else {
            System.out.println("Não foi possível obter as informações do alimento.");
        }
    }
}

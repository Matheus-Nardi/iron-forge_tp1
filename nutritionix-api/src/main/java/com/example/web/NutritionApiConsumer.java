package com.example.web;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.example.models.Root;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NutritionApiConsumer {

    private static final String API_URL = "https://trackapi.nutritionix.com/v2/search/item/?upc=";
    private static final String APP_ID = "69114c9a";
    private static final String APP_KEY = "b33e13b397d965a7897c87ec668bd16f";

    public Root getFoodInfoByUPC(String upcCode) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL + upcCode))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("x-app-id", APP_ID)
                    .header("x-app-key", APP_KEY)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper om = new ObjectMapper();

            Root root = om.readValue(response.body(), Root.class);

            return root;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

package br.unitins.tp1.ironforge.service.whey.tabelanutricional;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.unitins.tp1.ironforge.model.whey.tabelanutricional.Food;
import br.unitins.tp1.ironforge.model.whey.tabelanutricional.Root;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TabelaNutricionalServiceImpl implements TabelaNutricionalService {

    private final String API_URL = "https://trackapi.nutritionix.com/v2/search/item/?upc=";

    private static final String APP_ID = "69114c9a"; 
    private static final String APP_KEY = "b33e13b397d965a7897c87ec668bd16f";  

    private HttpClient client;

    @Override
    public Food getTabelaNutricional(String upc) {
        client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + upc))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("x-app-id", APP_ID)
                .header("x-app-key", APP_KEY)
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ObjectMapper om = new ObjectMapper();
                Root root = om.readValue(response.body(), Root.class);
               
                if (root.getFoods() != null && !root.getFoods().isEmpty()) {
                    return root.getFoods().get(0); 
                }
            } else {
                
                System.err.println("Erro ao chamar a API: " + response.statusCode() + " - " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace(); 
        }
        return null; 
    }
}

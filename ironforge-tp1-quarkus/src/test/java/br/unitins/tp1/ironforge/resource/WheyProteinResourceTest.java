package br.unitins.tp1.ironforge.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import br.unitins.tp1.ironforge.dto.whey.WheyProteinRequestDTO;
import br.unitins.tp1.ironforge.model.whey.WheyProtein;
import br.unitins.tp1.ironforge.service.whey.WheyProteinService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

@QuarkusTest
public class WheyProteinResourceTest {

    @Inject
    public WheyProteinService wheyService;

    @Test
    void testCreate() {
        WheyProteinRequestDTO dto = new WheyProteinRequestDTO("123456789", "Whey teste", "um whey para testes", 1L, 1,
                100.00, 1000, 1L);

        given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post("/wheys")
                .then()
                .statusCode(201)
                .body("nome", is("Whey teste"),
                        "fabricante.nome", is("Max Titanium"),
                        "sabor.nome", is("Morango"));

        wheyService.delete(wheyService.findByNome("Whey teste").getFirst().getId());

    }

    @Test
    void testDelete() {
        WheyProteinRequestDTO dto = new WheyProteinRequestDTO("123456789", "Whey teste", "um whey para testes", 1L, 1,
                100.00, 1000, 1L);

        Long id = wheyService.create(dto).getId();

        given()
                .when()
                .delete("/wheys/{id}", id)
                .then().statusCode(204);

        WheyProtein whey = wheyService.findById(id);
        assertNull(whey);

    }

    @Test
    void testFindById() {
        given()
                .when().get("/wheys/1")
                .then().statusCode(200)
                .body("id", is(1));
    }

    @Test
    void testFindByNome() {
        given()
                .when()
                .queryParam("nome", "Whey da Growth")
                .get("/wheys/search/nome")
                .then().statusCode(200)
                .body("[0].nome", is("Whey da Growth"), "$.size()", greaterThan(1));
    }

    @Test
    void testFindByPreco() {
        given()
                .when()
                .queryParam("preco", 100)
                .get("/wheys/search/preco")
                .then().statusCode(200)
                .body("[0].nome", is("Whey da Growth"), "$.size()", greaterThan(1));
    }

    @Test
    void testFindByPrecoMinAndMax() {
        given()
                .when()

                .get("/wheys/search/preco/min/{precoMin}/max/{precoMax}", 100, 150)
                .then().statusCode(200)
                .body("[0].nome", is("Whey da Growth"), "$.size()", greaterThan(2));
    }

    @Test
    void testFindBySabor() {
        given()
                .when()
                .queryParam("sabor", "Chocolate")
                .get("/wheys/search/sabor")
                .then().statusCode(200)
                .body("[0].nome", is("Whey da Growth"), "$.size()", is(1),
                        "[0].tipoWhey.label", is("Concentrado"));
    }

    @Test
    void testFindByTipoWhey() {
        given()
                .when()
                .queryParam("tipo", "ISOLADO")
                .get("/wheys/search/tipo")
                .then().statusCode(200)
                .body("[0].id", is(3), "[0].nome", is("Whey da Growth"),
                        "[0].tipoWhey.label", is("Isolado"));
    }

    @Test
    void testGetAll() {
        given()
                .when().get("/wheys")
                .then().statusCode(200);
    }

    @Test
    void testUpdate() {
        WheyProteinRequestDTO dto = new WheyProteinRequestDTO("123456789", "Whey teste", "um whey para testes", 1L, 1,
                100.00, 1000, 1L);

        Long id = wheyService.create(dto).getId();

        WheyProteinRequestDTO novoDto = new WheyProteinRequestDTO("123456789", "Whey novo",
                "um whey para testes atualizado", 2L, 2,
                500.00, 4000, 1L);

        given()
                .contentType(ContentType.JSON)
                .body(novoDto)
                .when()
                .put("/wheys/{id}" , id)
                .then()
                .statusCode(204);
        
        WheyProtein wheyProtein = wheyService.findById(id);
        assertEquals(wheyProtein.getNome(), novoDto.nome());
        assertEquals(wheyProtein.getPreco(), novoDto.preco());

        wheyService.delete(id);
    }
}

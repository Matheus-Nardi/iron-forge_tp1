package br.unitins.tp1.ironforge.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.ironforge.dto.whey.SaborRequestDTO;
import br.unitins.tp1.ironforge.model.whey.Sabor;
import br.unitins.tp1.ironforge.service.whey.sabor.SaborService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

@QuarkusTest
public class SaborResourceTest {

    @Inject
    public SaborService saborService;

    @Test
    @DisplayName("Deve retornar status code 201 para o metodo POST em /sabores")
    void testCreate() {
        SaborRequestDTO dto = new SaborRequestDTO("Doce de leite");

        given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post("/sabores")
                .then()
                .statusCode(201)
                .body("id", notNullValue(),
                        "nome", is("Doce de leite"));

        saborService.delete(saborService.findByNome("Doce de leite").get(0).getId());
    }

    @Test
    @DisplayName("Deve retornar status code 204 para o metodo DELETE em /sabores/{id}")
    void testDelete() {

        // Criando dado para ser deletado
        SaborRequestDTO dto = new SaborRequestDTO("Sabor para deleção");
        Long id = saborService.create(dto).getId();

        given()
                .when()
                .delete("/sabores/{id}", id)
                .then()
                .statusCode(204);

        Sabor sabor = saborService.findById(id);
        assertNull(sabor);
    }

    @Test
    @DisplayName("Deve retornar status code 200 para o metodo GET em /sabores")
    void testFindAll() {
        given()
                .when().get("/sabores")
                .then().statusCode(200);
    }

    @Test
    @DisplayName("Deve retornar status code 200 para o metodo GET em /sabores/{id}")
    void testFindById() {
        Long idExistente = 1L;

        given()
                .pathParam("id", idExistente)
                .when()
                .get("/sabores/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("Deve retornar status code 200 e o sabor correto para o metodo GET em /sabores/search/{nome}")
    void testFindByNome() {
        String saborExistente = "Chocolate";

        given()
                .pathParam("nome", saborExistente)
                .when()
                .get("/sabores/search/{nome}")
                .then()
                .statusCode(200)
                .body("$.size()", is(1),
                        "[0].nome", is("Chocolate"));

    }

    @Test
    @DisplayName("Deve retornar status code 204  para o metodo PUT em /sabores/{id}")
    void testUpdate() {
        SaborRequestDTO dto = new SaborRequestDTO("Sabor Teste");

        Long id = saborService.create(dto).getId();

        SaborRequestDTO novoSabor = new SaborRequestDTO("Sabor atualizado");

        given()
                .contentType(ContentType.JSON)
                .body(novoSabor)
                .when()
                .put("sabores/{id}", id)
                .then()
                .statusCode(204);

        Sabor sabor = saborService.findById(id);
        assertEquals(sabor.getNome(), novoSabor.nome());
        assertFalse(sabor.getNome().equals(dto.nome()));

        saborService.delete(saborService.findById(id).getId());
    }
}

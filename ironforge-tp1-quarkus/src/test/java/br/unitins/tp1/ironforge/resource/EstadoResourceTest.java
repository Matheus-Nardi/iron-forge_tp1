package br.unitins.tp1.ironforge.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import br.unitins.tp1.ironforge.dto.estado.EstadoRequestDTO;
import br.unitins.tp1.ironforge.model.endereco.Estado;
import br.unitins.tp1.ironforge.service.estado.EstadoService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

@QuarkusTest
public class EstadoResourceTest {

    @Inject
    public EstadoService estadoService;

    @Test
    void testCreate() {
        EstadoRequestDTO dto = new EstadoRequestDTO("Mato Grosso do Sul", "MS");

        given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post("/estados")
                .then()
                .statusCode(201)
                .body("nome", is("Mato Grosso do Sul"),
                        "sigla", is("MS"));

        estadoService.delete(estadoService.findByNome("Mato Grosso do Sul").getFirst().getId());
    }

    @Test
    void testDelete() {
        EstadoRequestDTO dto = new EstadoRequestDTO("Mato Grosso do Sul", "MS");

        Long id = estadoService.create(dto).getId();

        given()
                .when()
                .delete("/estados/{id}", id)
                .then().statusCode(204);

        Estado estado = estadoService.findById(id);
        assertNull(estado);
    }

    @Test
    void testFindAll() {
        given()
                .when()
                .get("/estados")
                .then()
                .statusCode(200);
    }

    @Test
    void testFindById() {
        given()
                .when().get("/estados/1")
                .then().statusCode(200)
                .body("id", is(1));
    }

    @Test
    void testFindByNome() {
        given()
                .when()
                .get("/estados/search/{nome}", "To")
                .then().statusCode(200)
                .body("[0].nome", is("Tocantins"), "[0].sigla", is("TO"));
    }


    @Test
    void testFindBySigla() {
        given()
                .when()
                .get("/estados/search/{sigla}", "To")
                .then().statusCode(200)
                .body("[0].nome", is("Tocantins"), "[0].sigla", is("TO"));
    }

    @Test
    void testUpdate() {
        EstadoRequestDTO dto = new EstadoRequestDTO("Mato Grosso do Sul", "MT");

        long id = estadoService.create(dto).getId();

        EstadoRequestDTO novoDto = new EstadoRequestDTO("Mato Grosso do Sul", "MS");

        given()
                .contentType(ContentType.JSON)
                .body(novoDto)
                .when()
                .put("/estados/{id}", id)
                .then()
                .statusCode(204);

        Estado estado = estadoService.findById(id);

        assertEquals(estado.getNome(), "Mato Grosso do Sul");
        assertEquals(estado.getSigla(), "MS");

        estadoService.delete(id);
    }
}

package br.unitins.tp1.ironforge.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import br.unitins.tp1.ironforge.dto.estado.EstadoRequestDTO;
import br.unitins.tp1.ironforge.model.endereco.Estado;
import br.unitins.tp1.ironforge.service.estado.EstadoService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

@QuarkusTest
public class EstadoResourceTest {

    @Inject
    public EstadoService estadoService;

    @Test
    @TestSecurity(user = "test", roles = "Funcionario")
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
    @TestSecurity(user = "test", roles = "Funcionario")
    public void deleteTest(){
        Long id = estadoService.create(new EstadoRequestDTO("Mato Grosso do Sul", "MS")).getId();

        given()
        .when()
        .pathParam("id", id)
        .delete("/estados/{id}")
        .then()
        .statusCode(204);
    }

    @Test
    @TestSecurity(user = "test", roles = "Funcionario")
    void testFindAll() {
        given()
                .when()
                .get("/estados")
                .then()
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "test", roles = "Funcionario")
    void testFindById() {
        given()
                .when().get("/estados/1")
                .then().statusCode(200)
                .body("id", is(1));
    }

    @Test
    @TestSecurity(user = "test", roles = "Funcionario")
    void testFindByNome() {
        given()
                .when()
                .get("/estados/search/nome/{nome}", "To")
                .then().statusCode(200)
                .body("[0].nome", is("Tocantins"), "[0].sigla", is("TO"));
    }

    @Test
    @TestSecurity(user = "test", roles = "Funcionario")
    void testFindBySigla() {
        given()
                .when()
                .get("/estados/search/sigla/{sigla}", "To")
                .then().statusCode(200)
                .body("nome", is("Tocantins"), "sigla", is("TO"));
    }

    @Test
    @TestSecurity(user = "test", roles = "Funcionario")
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

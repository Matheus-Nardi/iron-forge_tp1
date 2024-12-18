package br.unitins.tp1.ironforge.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import br.unitins.tp1.ironforge.dto.cidade.CidadeRequestDTO;
import br.unitins.tp1.ironforge.model.endereco.Cidade;
import br.unitins.tp1.ironforge.service.cidade.CidadeService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

@QuarkusTest
public class CidadeResourceTest {

    @Inject
    public CidadeService cidadeService;

    @Test
    @TestSecurity(user = "test", roles = { "Administrador", "Funcionario" })
    void testCreate() {

        CidadeRequestDTO dto = new CidadeRequestDTO("Monte do Carmo", 1L);

        given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post("/cidades")
                .then()
                .statusCode(201)
                .body("nome", is("Monte do Carmo"),
                        "estado.nome", is("Tocantins"),
                        "estado.sigla", is("TO"));

        cidadeService.delete(cidadeService.findByNome("Monte do Carmo").getFirst().getId());
    }

    @Test
    @TestSecurity(user = "test", roles = { "Administrador", "Funcionario" })
    void testDelete() {
        Long id = cidadeService.create(new CidadeRequestDTO("Torres Tortas", 1L)).getId();

        given()
                .when()
                .delete("/cidades/{id}", id)
                .then().statusCode(204);
    }

    @Test
    @TestSecurity(user = "test", roles = { "Administrador", "Funcionario" })
    void testFindAll() {
        given()
                .when().get("/cidades")
                .then().statusCode(200);
    }

    @Test
    @TestSecurity(user = "test", roles = { "Administrador", "Funcionario" })
    void testFindById() {
        given()
                .when().get("/cidades/1")
                .then().statusCode(200)
                .body("id", is(1));
    }

    @Test
    @TestSecurity(user = "test", roles = { "Administrador", "Funcionario" })
    void testFindByNome() {
        given()
                .when()
                .get("/cidades/search/{nome}", "Palmas")
                .then().statusCode(200)
                .body("nome", hasItem(is("Palmas")));
    }

    @Test
    @TestSecurity(user = "test", roles = { "Administrador", "Funcionario" })
    void testUpdate() {
        CidadeRequestDTO dto = new CidadeRequestDTO("Torres Tortas", 1l);
        Long id = cidadeService.create(dto).getId();

        CidadeRequestDTO novoDto = new CidadeRequestDTO("Torres Retas", 1l);

        given()
                .contentType(ContentType.JSON)
                .body(novoDto)
                .when()
                .put("/cidades/{id}", id)
                .then()
                .statusCode(204);

        Cidade cidade = cidadeService.findById(id);

        assertEquals(cidade.getNome(), "Torres Retas");

        cidadeService.delete(id);

    }
}

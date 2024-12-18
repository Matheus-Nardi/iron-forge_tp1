package br.unitins.tp1.ironforge.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import br.unitins.tp1.ironforge.dto.cupom.CupomRequestDTO;
import br.unitins.tp1.ironforge.model.pedido.Cupom;
import br.unitins.tp1.ironforge.service.cupom.CupomService;
import br.unitins.tp1.ironforge.validation.EntidadeNotFoundException;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

@QuarkusTest
public class CupomResourceTest {

    @Inject
    public CupomService cupomService;

    @Test
    @TestSecurity(user = "test", roles = { "Administrador", "Funcionario" })
    void testCreate() {
        CupomRequestDTO dto = new CupomRequestDTO(1L, "CUPOMTEST", 20.0, LocalDateTime.now().plusMonths(2L), true);

        given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when()
                .post("/cupons")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("codigo", is("CUPOMTEST"));

        cupomService.delete(cupomService.findByCodigo("CUPOMTEST").getFirst().getId());
    }

    @Test
    @TestSecurity(user = "test", roles = { "Administrador", "Funcionario" })
    void testDeactive() {
        CupomRequestDTO dto = new CupomRequestDTO(1L, "UPDATE", 10.0, LocalDateTime.now().plusWeeks(1L), true);
        Long idExistente = cupomService.create(dto).getId();

        given()
                .contentType(ContentType.JSON)
                .when()
                .patch("/cupons/{id}/desativacao", idExistente)
                .then()
                .statusCode(204);

        Cupom cupom = cupomService.findById(idExistente);
        assertFalse(cupom.getAtivo());
        cupomService.delete(idExistente);

    }

    @Test
    @TestSecurity(user = "test", roles = { "Administrador", "Funcionario" })
    void testDelete() {
        CupomRequestDTO dto = new CupomRequestDTO(1L, "DELETE", 10.0, LocalDateTime.now().plusMonths(1L), true);
        Long id = cupomService.create(dto).getId();

        given()
                .when()
                .delete("/cupons/{id}", id)
                .then()
                .statusCode(204);

        assertThrows(EntidadeNotFoundException.class, () -> cupomService.findById(id));
    }

    @Test
    @TestSecurity(user = "test", roles = { "Administrador", "Funcionario" })
    void testFindAll() {
        given()
                .when()
                .get("/cupons")
                .then()
                .statusCode(200);
    }

    @Test
    @TestSecurity(user = "test", roles = { "Administrador", "Funcionario" })
    void testBuscarPorCodigo() {
        given()
                .when()
                .get("/cupons/search/codigo/{codigo}", "WHEY10")
                .then()
                .statusCode(200)
                .body("[0].codigo", is("WHEY10"));
    }

    @Test
    @TestSecurity(user = "test", roles = { "Administrador", "Funcionario" })
    void testFindByFabricante() {
        Long idFabricante = 1L;

        given()
                .when()
                .get("/cupons/search/fabricante/{idFabricante}", idFabricante)
                .then()
                .statusCode(200)
                .body("[0].codigo", is("WHEY10"));

    }

    @Test
    @TestSecurity(user = "test", roles = { "Administrador", "Funcionario" })
    void testFindById() {
        Long idExistente = 1L;
        given()
                .when()
                .get("/cupons/{id}", idExistente)
                .then()
                .statusCode(200)
                .body("codigo", is("WHEY10"));
    }

    @Test
    @TestSecurity(user = "test", roles = { "Administrador", "Funcionario" })
    void testUpdate() {
        CupomRequestDTO dto = new CupomRequestDTO(1L, "UPDATE", 10.0, LocalDateTime.now().plusWeeks(1L), true);

        Long id = cupomService.create(dto).getId();

        CupomRequestDTO novoCupom = new CupomRequestDTO(2L, "UPDATE20", 20.0, LocalDateTime.now().plusWeeks(2L), true);

        given()
                .contentType(ContentType.JSON)
                .body(novoCupom)
                .when()
                .put("cupons/{id}", id)
                .then()
                .statusCode(204);

        Cupom cupom = cupomService.findById(id);
        assertEquals(cupom.getCodigo(), novoCupom.codigo());
        assertFalse(cupom.getPercentualDesconto().equals(dto.percentualDesconto()));

        cupomService.delete(cupomService.findById(id).getId());
    }
}

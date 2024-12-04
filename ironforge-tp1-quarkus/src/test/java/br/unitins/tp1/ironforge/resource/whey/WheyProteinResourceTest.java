package br.unitins.tp1.ironforge.resource.whey;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;

import br.unitins.tp1.ironforge.model.whey.TipoWhey;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class WheyProteinResourceTest {

    @Test
    void testFindByNome() {
        String nomeExistente = "Ultra Whey Supreme";

        given()
                .queryParam("nome", nomeExistente)
                .when()
                .get("/wheys/search/nome")
                .then()
                .statusCode(200)
                .body("[0].nome", is(nomeExistente));
    }

    @Test
    void testFindByPreco() {
        double precoMinimo = 139.90;

        given()
                .queryParam("preco", precoMinimo)
                .when()
                .get("/wheys/search/preco")
                .then()
                .statusCode(200);
    }

    @Test
    void testFindByPrecoMinAndMax() {
        double precoMin = 140.00;
        double precoMax = 160.00;

        given()
                .when()
                .get("wheys/search/preco/min/{precoMin}/max/{precoMax}" , precoMin, precoMax)
                .then()
                .statusCode(200);
    }

    @Test
    void testFindBySabor() {
        String saborExistente = "Baunilha";

        given()
                .queryParam("sabor", saborExistente)
                .when()
                .get("/wheys/search/sabor")
                .then()
                .statusCode(200)
                .body("[0].sabor.nome", is("Baunilha"));
    }

    @Test
    void testFindByTipoWhey() {

        given()
                .queryParam("tipo", TipoWhey.ISOLADO)
                .when()
                .get("/wheys/search/tipo")
                .then()
                .statusCode(200)
                .body("[0].tipoWhey.label", is("Isolado"));
    }

    @Test
    void testFindMostRated() {
        given()
                .when()
                .get("/wheys/search/nota")
                .then()
                .statusCode(200);
    }

    @Test
    void testGetAll() {
        given()
                .when()
                .get("/wheys")
                .then()
                .statusCode(200);
    }
}

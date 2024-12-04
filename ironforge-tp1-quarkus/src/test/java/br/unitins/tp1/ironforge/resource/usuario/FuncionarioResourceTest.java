package br.unitins.tp1.ironforge.resource.usuario;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

import br.unitins.tp1.ironforge.service.usuario.FuncionarioService;
import br.unitins.tp1.ironforge.service.usuario.UsuarioService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;

@QuarkusTest
public class FuncionarioResourceTest {

        @Inject
        FuncionarioService funcionarioService;
        @Inject
        UsuarioService usuarioService;

        @Test
        @TestSecurity(user = "admin", roles = { "Administrador" })
        void testFindAll() {
                given()
                                .when()
                                .get("/funcionarios")
                                .then()
                                .statusCode(200);
        }

        @Test
        @TestSecurity(user = "admin", roles = { "Administrador" })
        void testFindById() {
                given()
                                .when()
                                .get("/funcionarios/{id}", 1L)
                                .then()
                                .statusCode(200)
                                .body("nome", is("Maxuel Filho Pinto"));
        }

        @Test
        @TestSecurity(user = "admin", roles = { "Administrador" })
        void testFindByNome() {
                given()
                                .when()
                                .get("/funcionarios/search/{nome}", "Maxuel")
                                .then()
                                .statusCode(200)
                                .body("[0].id", is(1), "[0].nome", is("Maxuel Filho Pinto"));
        }

}

package br.unitins.tp1.ironforge.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import br.unitins.tp1.ironforge.dto.endereco.EnderecoRequestDTO;
import br.unitins.tp1.ironforge.dto.fabricante.FabricanteCreateRequestDTO;
import br.unitins.tp1.ironforge.dto.fabricante.FabricanteUpdateRequestDTO;
import br.unitins.tp1.ironforge.dto.telefone.TelefoneRequestDTO;
import br.unitins.tp1.ironforge.model.Fabricante;
import br.unitins.tp1.ironforge.service.fabricante.FabricanteService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

@QuarkusTest
public class FabricanteResourceTest {

        @Inject
        public FabricanteService fabricanteService;

        @Test
        void testCreate() {
                List<TelefoneRequestDTO> telefones = List.of(new TelefoneRequestDTO("63", "912345678"),
                                new TelefoneRequestDTO("85", "912355678"));

                List<EnderecoRequestDTO> enderecos = List
                                .of(new EnderecoRequestDTO(1L, "Teste", "Teste bairro", "Teste numero",
                                                "Teste comlemento", "121212"));

                FabricanteCreateRequestDTO dto = new FabricanteCreateRequestDTO("Empresa Teste", "40954681000141",
                                "empresa@gmail.com", telefones, enderecos);

                given()
                                .contentType(ContentType.JSON)
                                .body(dto)
                                .when()
                                .post("/fabricantes")
                                .then()
                                .statusCode(201)
                                .body("id", notNullValue(), "nome", is("Empresa Teste"));

                fabricanteService.delete(fabricanteService.findByNome("Empresa Teste").getFirst().getId());
        }

        @Test
        void testDelete() {
                List<TelefoneRequestDTO> telefones = List.of(new TelefoneRequestDTO("63", "912345678"),
                                new TelefoneRequestDTO("85", "912355678"));

                List<EnderecoRequestDTO> enderecos = List
                                .of(new EnderecoRequestDTO(1L, "Teste", "Teste bairro", "Teste numero",
                                                "Teste comlemento", "121212"));

                FabricanteCreateRequestDTO dto = new FabricanteCreateRequestDTO("Empresa Teste", "40954681000141",
                                "empresa@gmail.com", telefones, enderecos);

                Long id = fabricanteService.create(dto).getId();

                given()
                                .when()
                                .delete("/fabricantes/{id}", id)
                                .then()
                                .statusCode(204);

                Fabricante fabricante = fabricanteService.findById(id);
                assertNull(fabricante);
        }

        @Test
        void testFindAll() {
                given()
                                .when()
                                .get("/fabricantes")
                                .then()
                                .statusCode(200);
        }

        @Test
        void testFindById() {
                Long idExistente = 1L;
                given()
                                .when()
                                .get("/fabricantes/{id}", idExistente)
                                .then()
                                .statusCode(200)
                                .body("nome", is("Max Titanium"));
        }

        @Test
        void testFindByNome() {
                given()
                                .when()
                                .get("/fabricantes/search/{nome}", "Max")
                                .then()
                                .statusCode(200)
                                .body("[0].id", is(1), "[0].nome", is("Max Titanium"));
        }

        @Test
        void testUpdate() {
                List<TelefoneRequestDTO> telefones = List.of(new TelefoneRequestDTO("63", "912345678"),
                                new TelefoneRequestDTO("85", "912355678"));

                List<EnderecoRequestDTO> enderecos = List
                                .of(new EnderecoRequestDTO(1L, "Teste", "Teste bairro", "Teste numero",
                                                "Teste comlemento", "121212"));

                FabricanteCreateRequestDTO dto = new FabricanteCreateRequestDTO("Empresa Teste", "40954681000141",
                                "empresa@gmail.com", telefones, enderecos);

                Long id = fabricanteService.create(dto).getId();

                FabricanteUpdateRequestDTO novoFabricante = new FabricanteUpdateRequestDTO("Empresa Teste Atualizada ",
                                "40954681000141",
                                "empresaatualizada@gmail.com");
                given()
                                .contentType(ContentType.JSON)
                                .body(novoFabricante)
                                .when()
                                .patch("/fabricantes/{id}", id)
                                .then()
                                .statusCode(204);

                Fabricante fabricante = fabricanteService.findById(id);
                assertEquals(fabricante.getNome(), novoFabricante.nome());
                assertEquals(fabricante.getEmail(), novoFabricante.email());

                fabricanteService.delete(fabricanteService.findById(id).getId());
        }

}

package br.unitins.tp1.ironforge.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import br.unitins.tp1.ironforge.dto.endereco.EnderecoRequestDTO;
import br.unitins.tp1.ironforge.dto.pessoajuridica.FabricanteRequestDTO;
import br.unitins.tp1.ironforge.dto.pessoajuridica.FabricanteUpdateRequestDTO;
import br.unitins.tp1.ironforge.dto.telefone.TelefoneRequestDTO;
import br.unitins.tp1.ironforge.dto.usuario.UsuarioRequestDTO;
import br.unitins.tp1.ironforge.model.usuario.Fabricante;
import br.unitins.tp1.ironforge.service.fabricante.FabricanteService;
import br.unitins.tp1.ironforge.validation.EntidadeNotFoundException;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

@QuarkusTest
public class FabricanteResourceTest {

        @Inject
        public FabricanteService fabricanteService;

        @Test
        @TestSecurity(user = "test", roles = { "Funcionario", "Administrador" })
        void testCreate() {
                List<TelefoneRequestDTO> telefones = List.of(new TelefoneRequestDTO("63", "912345678"),
                                new TelefoneRequestDTO("85", "912355678"));

                List<EnderecoRequestDTO> enderecos = List
                                .of(new EnderecoRequestDTO(1L, "Teste", "Teste bairro", "Teste numero",
                                                "Teste comlemento", "121212"));

                FabricanteRequestDTO dto = new FabricanteRequestDTO("Empresa Teste", "07126830000109",
                                "empresa@gmail.com", telefones, enderecos,
                                new UsuarioRequestDTO("empresa.teste", "123456", "email@gmail.com"));

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
        @TestSecurity(user = "test", roles = { "Funcionario", "Administrador" })
        void testDelete() {
                List<TelefoneRequestDTO> telefones = List.of(new TelefoneRequestDTO("63", "912345678"),
                                new TelefoneRequestDTO("85", "912355678"));

                List<EnderecoRequestDTO> enderecos = List
                                .of(new EnderecoRequestDTO(1L, "Teste", "Teste bairro", "Teste numero",
                                                "Teste comlemento", "121212"));

                FabricanteRequestDTO dto = new FabricanteRequestDTO("Empresa Teste", "07126830000109",
                                "empresa@gmail.com", telefones, enderecos,
                                new UsuarioRequestDTO("empresa.teste", "123456", "email@gmail.com"));

                Long id = fabricanteService.create(dto).getId();

                given()
                                .when()
                                .delete("/fabricantes/{id}", id)
                                .then()
                                .statusCode(204);

                assertThrows(EntidadeNotFoundException.class, () -> fabricanteService.findById(id));
        }

        @Test
        @TestSecurity(user = "test", roles = { "Funcionario", "Administrador" })
        void testFindAll() {
                given()
                                .when()
                                .get("/fabricantes")
                                .then()
                                .statusCode(200);
        }

        @Test
        @TestSecurity(user = "test", roles = { "Funcionario", "Administrador" })
        void testFindById() {
                Long idExistente = 2L;
                given()
                                .when()
                                .get("/fabricantes/{id}", idExistente)
                                .then()
                                .statusCode(200)
                                .body("nome", is("Growth Suplementos"));
        }

        @Test
        @TestSecurity(user = "test", roles = { "Funcionario", "Administrador" })
        void testFindByNome() {
                given()
                                .when()
                                .queryParam("nome", "Growth")
                                .get("/fabricantes/search/nome")
                                .then()
                                .statusCode(200)
                                .body("[0].id", is(2), "[0].nome", is("Growth Suplementos"));
        }

        @Test
        @TestSecurity(user = "test", roles = { "Funcionario", "Administrador" })
        void testUpdate() {
                List<TelefoneRequestDTO> telefones = List.of(new TelefoneRequestDTO("63", "912345678"),
                                new TelefoneRequestDTO("85", "912355678"));

                List<EnderecoRequestDTO> enderecos = List
                                .of(new EnderecoRequestDTO(1L, "Teste", "Teste bairro", "Teste numero",
                                                "Teste comlemento", "121212"));

                FabricanteRequestDTO dto = new FabricanteRequestDTO("Empresa Teste", "07126830000109",
                                "empresa@gmail.com", telefones, enderecos,
                                new UsuarioRequestDTO("empresa.teste", "123456", "email@gmail.com"));

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
                assertEquals(fabricante.getPessoaJuridica().getNome(),
                                novoFabricante.nome());
                assertEquals(fabricante.getPessoaJuridica().getEmail(),
                                novoFabricante.email());

                fabricanteService.delete(fabricanteService.findById(id).getId());
        }

}

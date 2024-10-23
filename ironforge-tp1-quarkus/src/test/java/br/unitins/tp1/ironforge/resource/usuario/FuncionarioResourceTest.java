package br.unitins.tp1.ironforge.resource.usuario;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.unitins.tp1.ironforge.dto.endereco.EnderecoRequestDTO;
import br.unitins.tp1.ironforge.dto.telefone.TelefoneRequestDTO;
import br.unitins.tp1.ironforge.dto.usuario.UsuarioCreateRequestDTO;
import br.unitins.tp1.ironforge.dto.usuario.funcionario.FuncionarioCreateRequestDTO;
import br.unitins.tp1.ironforge.model.usuario.Funcionario;
import br.unitins.tp1.ironforge.model.usuario.Usuario;
import br.unitins.tp1.ironforge.service.usuario.FuncionarioService;
import br.unitins.tp1.ironforge.service.usuario.UsuarioService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

@QuarkusTest
public class FuncionarioResourceTest {

        @Inject
        public FuncionarioService funcionarioService;

        @Inject
        public UsuarioService usuarioService;

        @Test
        void testCreate() {

                List<TelefoneRequestDTO> telefones = List.of(new TelefoneRequestDTO("63", "912345678"),
                                new TelefoneRequestDTO("85", "912355678"));

                List<EnderecoRequestDTO> enderecos = List
                                .of(new EnderecoRequestDTO(1L, "Teste", "Teste bairro", "Teste numero",
                                                "Teste compleento", "121212"));

                UsuarioCreateRequestDTO userDto = new UsuarioCreateRequestDTO("Igor Giovanni", "22480428095",
                                "usuario@email.com",
                                "macarrão123", LocalDate.of(1990, 10, 10), telefones, enderecos);

                FuncionarioCreateRequestDTO dto = new FuncionarioCreateRequestDTO(userDto, BigDecimal.valueOf(2000));

                given()
                                .contentType(ContentType.JSON)
                                .body(dto)
                                .when()
                                .post("/funcionarios")
                                .then()
                                .statusCode(201)
                                .body("id", notNullValue(), "usuario.nome", is("Igor Giovanni"), "salario",
                                                is(2000));

                funcionarioService.delete(funcionarioService.findByNome("Igor Giovanni").getFirst().getId());
        }

        @Test
        void testDelete() {
                List<TelefoneRequestDTO> telefones = List.of(new TelefoneRequestDTO("63", "912345678"),
                                new TelefoneRequestDTO("85", "912355678"));

                List<EnderecoRequestDTO> enderecos = List
                                .of(new EnderecoRequestDTO(1L, "Teste", "Teste bairro", "Teste numero",
                                                "Teste comlemento", "121212"));

                UsuarioCreateRequestDTO userDto = new UsuarioCreateRequestDTO("Igor Giovanni Gael Pires", "22480428095",
                                "usuario@email.com",
                                "macarrão123", LocalDate.of(1990, 10, 10), telefones, enderecos);
                FuncionarioCreateRequestDTO dto = new FuncionarioCreateRequestDTO(userDto, BigDecimal.valueOf(2000));

                Long id = funcionarioService.create(dto).getId();
                given()
                                .when()
                                .delete("/funcionarios/{id}", id)
                                .then()
                                .statusCode(204);

                Funcionario funcionario = funcionarioService.findById(id);
                Usuario usuario = usuarioService.findById(id);
                assertNull(funcionario);
                assertNull(usuario);
        }

        @Test
        void testFindAll() {
                given()
                                .when()
                                .get("/funcionarios")
                                .then()
                                .statusCode(200);
        }

        @Test
        void testFindById() {
                given()
                                .when()
                                .get("/funcionarios/{id}", 1L)
                                .then()
                                .statusCode(200)
                                .body("usuario.nome", is("Matheus"));
        }

        @Test
        void testFindByNome() {
                given()
                                .when()
                                .get("/funcionarios/search/{nome}", "Matheus")
                                .then()
                                .statusCode(200)
                                .body("[0].id", is(1), "[0].usuario.nome", is("Matheus"));
        }

}

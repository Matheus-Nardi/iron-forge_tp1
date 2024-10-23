package br.unitins.tp1.ironforge.resource.usuario;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.unitins.tp1.ironforge.dto.endereco.EnderecoRequestDTO;
import br.unitins.tp1.ironforge.dto.telefone.TelefoneRequestDTO;
import br.unitins.tp1.ironforge.dto.usuario.UsuarioCreateRequestDTO;
import br.unitins.tp1.ironforge.dto.usuario.cliente.ClienteCreateRequestDTO;
import br.unitins.tp1.ironforge.model.usuario.Cliente;
import br.unitins.tp1.ironforge.model.usuario.Usuario;
import br.unitins.tp1.ironforge.service.usuario.ClienteService;
import br.unitins.tp1.ironforge.service.usuario.UsuarioService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

@QuarkusTest
public class ClienteResourceTest {

        @Inject
        public ClienteService clienteService;

        @Inject
        public UsuarioService usuarioService;

        @Test
        void testCreate() {

                List<TelefoneRequestDTO> telefones = List.of(new TelefoneRequestDTO("63", "912345678"),
                                new TelefoneRequestDTO("85", "912355678"));

                List<EnderecoRequestDTO> enderecos = List
                                .of(new EnderecoRequestDTO(1L, "Teste", "Teste bairro", "Teste numero",
                                                "Teste compleento", "121212"));

                UsuarioCreateRequestDTO userDto = new UsuarioCreateRequestDTO("Igor Giovanni Gael Pires", "22480428095",
                                "usuario@email.com",
                                "macarrão123", LocalDate.of(1990, 10, 10), telefones, enderecos);

                ClienteCreateRequestDTO dto = new ClienteCreateRequestDTO(userDto);

                given()
                                .contentType(ContentType.JSON)
                                .body(dto)
                                .when()
                                .post("/clientes")
                                .then()
                                .statusCode(201)
                                .body("id", notNullValue(), "usuario.nome", is("Igor Giovanni Gael Pires"));

                clienteService.delete(clienteService.findByNome("Igor Giovanni Gael Pires").getFirst().getId());
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
                ClienteCreateRequestDTO dto = new ClienteCreateRequestDTO(userDto);

                Long id = clienteService.create(dto).getId();
                given()
                                .when()
                                .delete("/clientes/{id}", id)
                                .then()
                                .statusCode(204);

                Cliente cliente = clienteService.findById(id);
                Usuario usuario = usuarioService.findById(id);
                assertNull(cliente);
                assertNull(usuario);
        }

        @Test
        void testFindAll() {
                given()
                                .when()
                                .get("/clientes")
                                .then()
                                .statusCode(200);
        }

        @Test
        void testFindById() {
                given()
                                .when()
                                .get("/clientes/{id}", 1L)
                                .then()
                                .statusCode(200)
                                .body("usuario.nome", is("Pedro"));
        }

        @Test
        void testFindByNome() {
                given()
                                .when()
                                .get("/clientes/search/{nome}", "Pedro")
                                .then()
                                .statusCode(200)
                                .body("[0].id", is(1), "[0].usuario.nome", is("Pedro"));
        }

       
}

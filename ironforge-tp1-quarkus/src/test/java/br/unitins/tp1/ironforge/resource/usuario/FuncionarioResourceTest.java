package br.unitins.tp1.ironforge.resource.usuario;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.unitins.tp1.ironforge.dto.endereco.EnderecoRequestDTO;
import br.unitins.tp1.ironforge.dto.pessoafisica.FuncionarioRequestDTO;
import br.unitins.tp1.ironforge.dto.pessoafisica.FuncionarioUpdateRequestDTO;
import br.unitins.tp1.ironforge.dto.telefone.TelefoneRequestDTO;
import br.unitins.tp1.ironforge.dto.usuario.UsuarioRequestDTO;
import br.unitins.tp1.ironforge.model.usuario.Funcionario;
import br.unitins.tp1.ironforge.service.usuario.FuncionarioService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

@QuarkusTest
public class FuncionarioResourceTest {

        // @Inject
        // public FuncionarioService funcionarioService;

        // @Test
        // void testCreate() {

        //         List<TelefoneRequestDTO> telefones = List.of(new TelefoneRequestDTO("63", "912345678"),
        //                         new TelefoneRequestDTO("85", "912355678"));

        //         List<EnderecoRequestDTO> enderecos = List
        //                         .of(new EnderecoRequestDTO(1L, "Teste", "Teste bairro", "Teste numero",
        //                                         "Teste compleento", "121212"));

        //         FuncionarioRequestDTO dto = new FuncionarioRequestDTO("Funcionario", "27263640040",
        //                         "funcionario@gmail.com",
        //                         LocalDate.of(1990, 10, 10), telefones, enderecos,
        //                         new UsuarioRequestDTO("funcionario.dto", "pato branco"), BigDecimal.valueOf(1000),
        //                         LocalDate.of(2024, 07, 20), "Cargo de funcionario");
        //         given()
        //                         .contentType(ContentType.JSON)
        //                         .body(dto)
        //                         .when()
        //                         .post("/funcionarios")
        //                         .then()
        //                         .statusCode(201)
        //                         .body("id", notNullValue(), "nome", is("Funcionario"), "salario",
        //                                         is(1000));

        //         funcionarioService.delete(funcionarioService.findByNome("Funcionario").getFirst().getId());
        // }

        // @Test
        // void testDelete() {
        //         List<TelefoneRequestDTO> telefones = List.of(new TelefoneRequestDTO("63", "912345678"),
        //                         new TelefoneRequestDTO("85", "912355678"));

        //         List<EnderecoRequestDTO> enderecos = List
        //                         .of(new EnderecoRequestDTO(1L, "Teste", "Teste bairro", "Teste numero",
        //                                         "Teste comlemento", "121212"));

        //         FuncionarioRequestDTO dto = new FuncionarioRequestDTO("Funcionario", "27263640040",
        //                         "funcionario@gmail.com",
        //                         LocalDate.of(1990, 10, 10), telefones, enderecos,
        //                         new UsuarioRequestDTO("funcionario.dto", "pato branco"), BigDecimal.valueOf(1000),
        //                         LocalDate.of(2024, 07, 20), "Cargo de funcionario");
        //         Long id = funcionarioService.create(dto).getId();
        //         given()
        //                         .when()
        //                         .delete("/funcionarios/{id}", id)
        //                         .then()
        //                         .statusCode(204);

        //         Funcionario funcionario = funcionarioService.findById(id);
        //         assertNull(funcionario);
        // }

        // @Test
        // void testFindAll() {
        //         given()
        //                         .when()
        //                         .get("/funcionarios")
        //                         .then()
        //                         .statusCode(200);
        // }

        // @Test
        // void testFindById() {
        //         given()
        //                         .when()
        //                         .get("/funcionarios/{id}", 1L)
        //                         .then()
        //                         .statusCode(200)
        //                         .body("nome", is("Maxuel Filho Pinto"));
        // }

        // @Test
        // void testFindByNome() {
        //         given()
        //                         .when()
        //                         .get("/funcionarios/search/{nome}", "Maxuel")
        //                         .then()
        //                         .statusCode(200)
        //                         .body("[0].id", is(1), "[0].nome", is("Maxuel Filho Pinto"));
        // }

        // @Test
        // void testUpdate() {
        //         List<TelefoneRequestDTO> telefones = List.of(new TelefoneRequestDTO("63", "912345678"),
        //                         new TelefoneRequestDTO("85", "912355678"));

        //         List<EnderecoRequestDTO> enderecos = List
        //                         .of(new EnderecoRequestDTO(1L, "Teste", "Teste bairro", "Teste numero",
        //                                         "Teste comlemento", "121212"));

        //         FuncionarioRequestDTO dto = new FuncionarioRequestDTO("Funcionario", "27263640040",
        //                         "funcionario@gmail.com",
        //                         LocalDate.of(1990, 10, 10), telefones, enderecos,
        //                         new UsuarioRequestDTO("funcionario.dto", "pato branco"), BigDecimal.valueOf(1000),
        //                         LocalDate.of(2024, 07, 20), "Cargo de funcionario");

        //         Long id = funcionarioService.create(dto).getId();

        //         FuncionarioUpdateRequestDTO novoFuncionario = new FuncionarioUpdateRequestDTO("Funcionario Novo",
        //                         "27263640040",
        //                         "funcionario@gmail.com", LocalDate.of(1990, 07, 20), LocalDate.of(2024, 10, 02),
        //                         "cargo", BigDecimal.valueOf(4000));
        //         given()
        //                         .contentType(ContentType.JSON)
        //                         .body(novoFuncionario)
        //                         .when()
        //                         .patch("/funcionarios/{id}", id)
        //                         .then()
        //                         .statusCode(204);

        //         Funcionario funcionario = funcionarioService.findById(id);
        //         assertEquals(funcionario.getPessoaFisica().getNome(), novoFuncionario.nome());
        //         assertEquals(funcionario.getCargo(), novoFuncionario.cargo());

        //         funcionarioService.delete(funcionarioService.findById(id).getId());
        // }

}

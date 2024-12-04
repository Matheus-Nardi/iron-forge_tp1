package br.unitins.tp1.ironforge.resource.usuario;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class ClienteResourceTest {

        // @Inject
        // public ClienteService clienteService;

        // @Test
        // void testCreate() {

        //         List<TelefoneRequestDTO> telefones = List.of(new TelefoneRequestDTO("63", "912345678"),
        //                         new TelefoneRequestDTO("85", "912355678"));

        //         List<EnderecoRequestDTO> enderecos = List
        //                         .of(new EnderecoRequestDTO(1L, "Teste", "Teste bairro", "Teste numero",
        //                                         "Teste compleento", "121212"));

        //         ClienteRequestDTO dto = new ClienteRequestDTO("Cliente", "27263640040", "cliente@gmail.com",
        //                         LocalDate.of(1990, 10, 10), telefones, enderecos,
        //                         new UsuarioRequestDTO("cliente.dto", "pato branco"));
        //         given()
        //                         .contentType(ContentType.JSON)
        //                         .body(dto)
        //                         .when()
        //                         .post("/clientes")
        //                         .then()
        //                         .statusCode(201)
        //                         .body("id", notNullValue(), "nome", is("Cliente"));

        //         clienteService.delete(clienteService.findByNome("Cliente").getFirst().getId());
        // }

        // @Test
        // void testDelete() {

        //         List<TelefoneRequestDTO> telefones = List.of(new TelefoneRequestDTO("63", "912345678"),
        //                         new TelefoneRequestDTO("85", "912355678"));

        //         List<EnderecoRequestDTO> enderecos = List
        //                         .of(new EnderecoRequestDTO(1L, "Teste", "Teste bairro", "Teste numero",
        //                                         "Teste comlemento", "121212"));

        //         ClienteRequestDTO dto = new ClienteRequestDTO("Cliente", "27263640040", "cliente@gmail.com",
        //                         LocalDate.of(1990, 10, 10), telefones, enderecos,
        //                         new UsuarioRequestDTO("cliente.dto", "pato branco"));

        //         Long id = clienteService.create(dto).getId();
        //         given()
        //                         .when()
        //                         .delete("/clientes/{id}", id)
        //                         .then()
        //                         .statusCode(204);

        //         Cliente cliente = clienteService.findById(id);
        //         assertNull(cliente);
        // }

        // @Test
        // void testFindAll() {
        //         given()
        //                         .when()
        //                         .get("/clientes")
        //                         .then()
        //                         .statusCode(200);
        // }

        // @Test
        // void testFindById() {
        //         given()
        //                         .when()
        //                         .get("/clientes/{id}", 1L)
        //                         .then()
        //                         .statusCode(200)
        //                         .body("nome", is("João Silva"));
        // }

        // @Test
        // void testFindByNome() {
        //         given()
        //                         .when()
        //                         .get("/clientes/search/{nome}", "João")
        //                         .then()
        //                         .statusCode(200)
        //                         .body("[0].id", is(1), "[0].nome", is("João Silva"));
        // }

        // @Test
        // void testUpdate() {
        //         List<TelefoneRequestDTO> telefones = List.of(new TelefoneRequestDTO("63", "912345678"),
        //                         new TelefoneRequestDTO("85", "912355678"));

        //         List<EnderecoRequestDTO> enderecos = List
        //                         .of(new EnderecoRequestDTO(1L, "Teste", "Teste bairro", "Teste numero",
        //                                         "Teste comlemento", "121212"));

        //         ClienteRequestDTO dto = new ClienteRequestDTO("Cliente", "27263640040",
        //                         "cliente@gmail.com",
        //                         LocalDate.of(1990, 10, 10), telefones, enderecos,
        //                         new UsuarioRequestDTO("cliente.dto", "pato branco"));

        //         Long id = clienteService.create(dto).getId();

        //         ClienteUpdateRequestDTO novoCliente = new ClienteUpdateRequestDTO("Cliente Novo",
        //                         "27263640040",
        //                         "clienteNovo@gmail.com", LocalDate.of(1990, 07, 20));
        //         given()
        //                         .contentType(ContentType.JSON)
        //                         .body(novoCliente)
        //                         .when()
        //                         .patch("/clientes/{id}", id)
        //                         .then()
        //                         .statusCode(204);

        //         Cliente cliente = clienteService.findById(id);
        //         assertEquals(cliente.getPessoaFisica().getNome(), novoCliente.nome());
        //         assertEquals(cliente.getPessoaFisica().getDataNascimento(), novoCliente.dataNascimento());

        //         clienteService.delete(clienteService.findById(id).getId());
        // }

}

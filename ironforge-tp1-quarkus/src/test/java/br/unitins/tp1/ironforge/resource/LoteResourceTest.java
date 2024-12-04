package br.unitins.tp1.ironforge.resource;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class LoteResourceTest {

    // @Inject
    // public LoteService loteService;

    // @Test
    // void testCreate() {
    //     LoteRequestDTO dto = new LoteRequestDTO(100, LocalDate.now(), 1L);

    //     given()
    //             .contentType(ContentType.JSON)
    //             .body(dto)
    //             .when()
    //             .post("/lotes")
    //             .then()
    //             .statusCode(201)
    //             .body("id", notNullValue())
    //             .body("quantidade", is(100));

    //     loteService.delete(loteService.findByWhey(1L).get(1).getId());

    // }

    // @Test
    // void testDelete() {
    //     LoteRequestDTO dto = new LoteRequestDTO(10, LocalDate.now(), 1L);
    //     Long id = loteService.create(dto).getId();

    //     given()
    //             .when()
    //             .delete("/lotes/{id}", id)
    //             .then()
    //             .statusCode(204);

    //     Lote lote = loteService.findById(id);
    //     assertNull(lote);
    // }

    // @Test
    // void testFindByCodigo() {

    //     given()
    //             .queryParam("codigo", "COD-WHEY-001")
    //             .when()
    //             .get("/lotes/search/codigo")
    //             .then()
    //             .statusCode(200)
    //             .body("[0].id", is(1))
    //             .body("[0].quantidade", is(100))
    //             .body("[0].wheyProtein.nome", is("Ultra Whey Supreme"));

    // }

    // @Test
    // void testFindById() {
    //     Long idExistente = 1L;
    //     given()
    //             .when()
    //             .get("/lotes/{id}", idExistente)
    //             .then()
    //             .statusCode(200);
    // }

    // @Test
    // void testFindByWhey() {
    //     given()
    //             .queryParam("idWhey", 1)
    //             .when()
    //             .get("/lotes/search/whey")
    //             .then()
    //             .statusCode(200)
    //             .body("[0].id", is(1))
    //             .body("[0].quantidade", is(100))
    //             .body("[0].wheyProtein.nome", is("Ultra Whey Supreme"));
    // }

    // @Test
    // void testGetAll() {
    //     given()
    //             .when()
    //             .get("/lotes")
    //             .then()
    //             .statusCode(200);
    // }

    // @Test
    // void testUpdate() {

    //     LoteRequestDTO dto = new LoteRequestDTO(10, LocalDate.now(), 1L);

    //     Long id = loteService.create(dto).getId();

    //     LoteRequestDTO novoLote = new LoteRequestDTO(100, LocalDate.now(), 2L);

    //     given()
    //             .contentType(ContentType.JSON)
    //             .body(novoLote)
    //             .when()
    //             .put("lotes/{id}", id)
    //             .then()
    //             .statusCode(204);

    //     Lote lote = loteService.findById(id);
    //     assertEquals(lote.getQuantidade(), novoLote.quantidade());
    //     assertFalse(lote.getWheyProtein().getId().equals(dto.idWhey()));

    //     loteService.delete(loteService.findById(id).getId());
    // }
}

package br.unitins.tp1.ironforge.initializer;

// @ApplicationScoped
// public class DataInitializer {

//     @Inject
//     public WheyProteinRepository wheyProteinRepository;

//     @Inject
//     public SaborRepository saborRepository;

//     @Inject
//     public FabricanteRepository fabricanteRepository;

//     @Inject
//     public TabelaNutricionalService tabelaNutricionalService;

//     @Inject
//     public LoteRepository loteRepository;

//     @Transactional
//     public void loadData(@Observes StartupEvent event) {

//         WheyProtein whey1 = new WheyProtein();
//         whey1.setUpc("748927024111");
//         whey1.setNome("Ultra Whey Supreme");
//         whey1.setDescricao("Proteína de alta qualidade com sabor de chocolate.");
//         whey1.setPreco(149.90);
//         whey1.setPeso(900);
//         whey1.setSabor(saborRepository.findById(1L));
//         whey1.setTipoWhey(TipoWhey.CONCENTRADO);
//         whey1.setFabricante(fabricanteRepository.findById(1L));
//         whey1.setFood(tabelaNutricionalService.getTabelaNutricional(whey1.getUpc()));

//         WheyProtein whey2 = new WheyProtein();
//         whey2.setUpc("602059001504");
//         whey2.setNome("Delícia de Chocolate Whey");
//         whey2.setDescricao("Sabor incrível de chocolate, ideal para smoothies.");
//         whey2.setPreco(139.90);
//         whey2.setPeso(900);
//         whey2.setSabor(saborRepository.findById(2L));
//         whey2.setTipoWhey(TipoWhey.CONCENTRADO);
//         whey2.setFabricante(fabricanteRepository.findById(2L));
//         whey2.setFood(tabelaNutricionalService.getTabelaNutricional(whey2.getUpc()));

//         WheyProtein whey3 = new WheyProtein();
//         whey3.setUpc("853414006430");
//         whey3.setNome("Baunilha Mágica");
//         whey3.setDescricao("Whey de baunilha com ingredientes que aumentam o foco.");
//         whey3.setPreco(154.90);
//         whey3.setPeso(1000);
//         whey3.setSabor(saborRepository.findById(3L));
//         whey3.setTipoWhey(TipoWhey.ISOLADO);
//         whey3.setFabricante(fabricanteRepository.findById(1L));
//         whey3.setFood(tabelaNutricionalService.getTabelaNutricional(whey3.getUpc()));

//         WheyProtein whey4 = new WheyProtein();
//         whey4.setUpc("602059001504");
//         whey4.setNome("Cookies Whey Delight");
//         whey4.setDescricao("Combinação perfeita de cookies e proteína.");
//         whey4.setPreco(159.90);
//         whey4.setPeso(800);
//         whey4.setSabor(saborRepository.findById(5L));
//         whey4.setTipoWhey(TipoWhey.ISOLADO);
//         whey4.setFabricante(fabricanteRepository.findById(2L));
//         whey4.setFood(tabelaNutricionalService.getTabelaNutricional(whey4.getUpc()));

//         WheyProtein whey5 = new WheyProtein();
//         whey5.setUpc("748927024111");
//         whey5.setNome("Neutro Energizante");
//         whey5.setDescricao("Sabor neutro para misturar com qualquer receita.");
//         whey5.setPreco(129.90);
//         whey5.setPeso(900);
//         whey5.setSabor(saborRepository.findById(4L));
//         whey5.setTipoWhey(TipoWhey.HIDROLISADO);
//         whey5.setFabricante(fabricanteRepository.findById(1L));
//         whey5.setFood(tabelaNutricionalService.getTabelaNutricional(whey5.getUpc()));

//         WheyProtein whey6 = new WheyProtein();
//         whey6.setUpc("748927024111");
//         whey6.setNome("Trem de Chocolate");
//         whey6.setDescricao("Experimente a explosão de chocolate na sua proteína.");
//         whey6.setPreco(169.90);
//         whey6.setPeso(950);
//         whey6.setSabor(saborRepository.findById(1L));
//         whey6.setTipoWhey(TipoWhey.HIDROLISADO);
//         whey6.setFabricante(fabricanteRepository.findById(2L));
//         whey6.setFood(tabelaNutricionalService.getTabelaNutricional(whey6.getUpc()));

//         // Persistindo no repositório
//         wheyProteinRepository.persist(whey1);
//         wheyProteinRepository.persist(whey2);
//         wheyProteinRepository.persist(whey3);
//         wheyProteinRepository.persist(whey4);
//         wheyProteinRepository.persist(whey5);
//         wheyProteinRepository.persist(whey6);

//         Lote lote1 = new Lote();
//         lote1.setQuantidade(100);
//         lote1.setDataFabricacao(LocalDate.of(2024, 1, 10));
//         lote1.setWheyProtein(whey1);
//         lote1.setCodigo("COD-WHEY-001");

//         Lote lote2 = new Lote();
//         lote2.setQuantidade(150);
//         lote2.setDataFabricacao(LocalDate.of(2024, 1, 12));
//         lote2.setWheyProtein(whey1);
//         lote2.setCodigo("COD-WHEY-002");

//         Lote lote3 = new Lote();
//         lote3.setQuantidade(200);
//         lote3.setDataFabricacao(LocalDate.of(2024, 1, 15));
//         lote3.setWheyProtein(whey2);
//         lote3.setCodigo("COD-WHEY-003");

//         Lote lote4 = new Lote();
//         lote4.setQuantidade(180);
//         lote4.setDataFabricacao(LocalDate.of(2024, 1, 20));
//         lote4.setWheyProtein(whey2);
//         lote4.setCodigo("COD-WHEY-004");

//         Lote lote5 = new Lote();
//         lote5.setQuantidade(120);
//         lote5.setDataFabricacao(LocalDate.of(2024, 1, 18));
//         lote5.setWheyProtein(whey3);
//         lote5.setCodigo("COD-WHEY-005");

//         Lote lote6 = new Lote();
//         lote6.setQuantidade(90);
//         lote6.setDataFabricacao(LocalDate.of(2024, 1, 22));
//         lote6.setWheyProtein(whey4);
//         lote6.setCodigo("COD-WHEY-006");

//         Lote lote7 = new Lote();
//         lote7.setQuantidade(160);
//         lote7.setDataFabricacao(LocalDate.of(2024, 1, 25));
//         lote7.setWheyProtein(whey5);
//         lote7.setCodigo("COD-WHEY-007");

//         Lote lote8 = new Lote();
//         lote8.setQuantidade(140);
//         lote8.setDataFabricacao(LocalDate.of(2024, 1, 28));
//         lote8.setWheyProtein(whey6);
//         lote8.setCodigo("COD-WHEY-008");

//         // Persistir os lotes
//         loteRepository.persist(lote1);
//         loteRepository.persist(lote2);
//         loteRepository.persist(lote3);
//         loteRepository.persist(lote4);
//         loteRepository.persist(lote5);
//         loteRepository.persist(lote6);
//         loteRepository.persist(lote7);
//         loteRepository.persist(lote8);
//     }
// }
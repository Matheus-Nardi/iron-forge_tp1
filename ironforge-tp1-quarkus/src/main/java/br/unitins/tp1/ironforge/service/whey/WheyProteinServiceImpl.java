package br.unitins.tp1.ironforge.service.whey;

import java.util.ArrayList;
import java.util.List;

import br.unitins.tp1.ironforge.dto.whey.WheyProteinRequestDTO;
import br.unitins.tp1.ironforge.model.whey.TipoWhey;
import br.unitins.tp1.ironforge.model.whey.WheyProtein;
import br.unitins.tp1.ironforge.model.whey.tabelanutricional.Food;
import br.unitins.tp1.ironforge.repository.WheyProteinRepository;
import br.unitins.tp1.ironforge.service.fabricante.FabricanteService;
import br.unitins.tp1.ironforge.service.whey.sabor.SaborService;
import br.unitins.tp1.ironforge.service.whey.tabelanutricional.TabelaNutricionalService;
import br.unitins.tp1.ironforge.validation.EntidadeNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class WheyProteinServiceImpl implements WheyProteinService {

    @Inject
    public WheyProteinRepository wheyRepository;

    @Inject
    public SaborService saborService;

    @Inject
    public FabricanteService fabricanteService;

    @Inject
    public TabelaNutricionalService tabelaNutricionalService;

    @Override
    public WheyProtein findById(Long id) {
        WheyProtein whey = wheyRepository.findById(id);
        if (whey == null)
            throw new EntidadeNotFoundException("id", "Whey não encontrado!");

        return whey;
    }

    @Override
    public List<WheyProtein> findByNome(String nome) {
        return wheyRepository.findByNome(nome);
    }

    @Override
    public List<WheyProtein> findByPreco(Double preco) {
        return wheyRepository.findByPreco(preco);
    }

    @Override
    public List<WheyProtein> findAll() {
        return wheyRepository.listAll();
    }

    @Override
    @Transactional
    public WheyProtein create(WheyProteinRequestDTO dto) {
        Food tabela = tabelaNutricionalService.getTabelaNutricional(dto.upc());

        WheyProtein whey = new WheyProtein();
        if (tabela == null) {
            throw new EntidadeNotFoundException(
                    "upc",
                    "O código UPC fornecido é invalído. Uma lista de códigos pode ser acessada em: https://www.barcodespider.com/whey");
        }
        whey.setNome(dto.nome());
        whey.setDescricao(dto.descricao());
        whey.setPeso(dto.peso());
        whey.setPreco(dto.preco());
        whey.setSabor(saborService.findById(dto.idSabor()));
        whey.setFabricante(fabricanteService.findById(dto.idFabricante()));
        whey.setTipoWhey(TipoWhey.valueOf(dto.idTipo()));
        whey.setFood(tabelaNutricionalService.getTabelaNutricional(dto.upc()));

        wheyRepository.persist(whey);
        return whey;
    }

    @Override
    @Transactional
    public void update(Long id, WheyProteinRequestDTO dto) {
        WheyProtein wheyToUpdate = wheyRepository.findById(id);
        if (wheyToUpdate == null)
            throw new EntidadeNotFoundException("id", "Whey não encontrado!");
        wheyToUpdate.setNome(dto.nome());
        wheyToUpdate.setDescricao(dto.descricao());
        wheyToUpdate.setPreco(dto.preco());
        wheyToUpdate.setPeso(dto.peso());
        wheyToUpdate.setSabor(saborService.findById(dto.idSabor()));
        wheyToUpdate.setFabricante(fabricanteService.findById(dto.idFabricante()));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        WheyProtein wheyToDelete = wheyRepository.findById(id);
        if (wheyToDelete == null)
            throw new EntidadeNotFoundException("id", "Whey não encontrado!");
        wheyRepository.delete(wheyToDelete);
    }

    @Override
    public List<WheyProtein> findByPrecoMinAndMax(Double precoMin, Double precoMax) {
        return wheyRepository.findByPrecoMinAndMax(precoMin, precoMax);
    }

    @Override
    public List<WheyProtein> findBySabor(String sabor) {
        return wheyRepository.findBySabor(sabor);
    }

    @Override
    public List<WheyProtein> findByTipoWhey(TipoWhey tipo) {
        return wheyRepository.findByTipo(tipo);
    }

    @Override
    @Transactional
    public void updateNomeImagem(Long id, String nomeImagem) {
        WheyProtein whey = wheyRepository.findById(id);

        if (whey.getImagens() == null) {
            whey.setImagens(new ArrayList<>());
            return;
        }

        whey.getImagens().add(nomeImagem);
    }

}

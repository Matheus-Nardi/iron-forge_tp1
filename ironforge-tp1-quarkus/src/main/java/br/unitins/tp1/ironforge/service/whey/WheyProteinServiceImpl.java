package br.unitins.tp1.ironforge.service.whey;

import java.util.List;

import br.unitins.tp1.ironforge.dto.whey.WheyProteinRequestDTO;
import br.unitins.tp1.ironforge.model.whey.WheyProtein;
import br.unitins.tp1.ironforge.repository.WheyProteinRepository;
import br.unitins.tp1.ironforge.service.fabricante.FabricanteService;
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

    @Override
    public WheyProtein findById(Long id) {
        return wheyRepository.findById(id);

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
        WheyProtein whey = new WheyProtein();
        whey.setNome(dto.nome());
        whey.setDescricao(String.format("Whey de %d da %s" , dto.peso() , fabricanteService.findById(dto.idFabricante()).getNome()));
        whey.setPeso(dto.peso());
        whey.setPreco(dto.preco());
        whey.setSabor(saborService.findById(dto.idSabor()));
        whey.setFabricante(fabricanteService.findById(dto.idFabricante()));
        wheyRepository.persist(whey);
        return whey;
    }

    @Override
    @Transactional
    public void update(Long id, WheyProteinRequestDTO dto) {
        WheyProtein wheyToUpdate = wheyRepository.findById(id);
        if (wheyToUpdate == null)
            throw new IllegalArgumentException("Whey não encontrado!");
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
            throw new IllegalArgumentException("Whey não encontrado!");
        wheyRepository.delete(wheyToDelete);
    }

}

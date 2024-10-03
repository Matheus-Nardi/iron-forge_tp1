package br.unitins.tp1.ironforge.service.whey;

import java.util.List;

import br.unitins.tp1.ironforge.dto.whey.WheyProteinRequestDTO;
import br.unitins.tp1.ironforge.model.WheyProtein;
import br.unitins.tp1.ironforge.repository.WheyProteinRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class WheyProteinServiceImpl implements WheyProteinService {

    @Inject
    public WheyProteinRepository wheyRepository;

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
    public WheyProtein create(WheyProteinRequestDTO wheyProtein) {
        WheyProtein whey = new WheyProtein(wheyProtein);
        wheyRepository.persist(whey);
        return whey;
    }

    @Override
    @Transactional
    public void update(Long id, WheyProteinRequestDTO wheyProtein) {
        WheyProtein wheyToUpdate = wheyRepository.findById(id);
        if (wheyToUpdate == null)
            throw new IllegalArgumentException("Whey não encontrado!");
        wheyToUpdate.setNome(wheyProtein.nome());
        wheyToUpdate.setDescricao(wheyProtein.descricao());
        wheyToUpdate.setPreco(wheyProtein.preco());
        wheyToUpdate.setPeso(wheyProtein.peso());
        wheyRepository.persist(wheyToUpdate);
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

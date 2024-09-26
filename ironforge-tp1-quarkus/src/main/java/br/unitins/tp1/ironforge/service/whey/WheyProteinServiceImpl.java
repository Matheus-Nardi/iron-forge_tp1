package br.unitins.tp1.ironforge.service.whey;

import java.util.List;

import br.unitins.tp1.ironforge.dto.whey.WheyProteinDTO;
import br.unitins.tp1.ironforge.dto.whey.WheyProteinResponseDTO;
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
    public WheyProteinResponseDTO findById(Long id) {
        WheyProtein whey = wheyRepository.findById(id);
        return WheyProteinResponseDTO.valueOf(whey);
    }

    @Override
    public List<WheyProteinResponseDTO> findByNome(String nome) {
        List<WheyProtein> wheys = wheyRepository.findByNome(nome);
        return wheys.stream()
                .map(whey -> WheyProteinResponseDTO.valueOf(whey)).toList();
    }

    @Override
    public List<WheyProteinResponseDTO> findByPreco(Double preco) {
        List<WheyProtein> wheys = wheyRepository.findByPreco(preco);
        return wheys.stream()
                .map(whey -> WheyProteinResponseDTO.valueOf(whey)).toList();
    }

    @Override
    public List<WheyProteinResponseDTO> findAll() {
        List<WheyProtein> wheys = wheyRepository.listAll();
        return wheys.stream()
                .map(whey -> WheyProteinResponseDTO.valueOf(whey)).toList();
    }

    @Override
    @Transactional
    public WheyProteinResponseDTO create(WheyProteinDTO wheyProtein) {
        WheyProtein whey = new WheyProtein(wheyProtein);

        wheyRepository.persist(whey);
        return WheyProteinResponseDTO.valueOf(whey);
    }

    @Override
    @Transactional
    public void update(Long id, WheyProteinDTO wheyProtein) {
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

package br.unitins.tp1.ironforge.service.lote;

import java.time.LocalDate;

import java.util.List;

import br.unitins.tp1.ironforge.dto.lote.LoteRequestDTO;

import br.unitins.tp1.ironforge.model.Lote;

import br.unitins.tp1.ironforge.repository.LoteRepository;

import br.unitins.tp1.ironforge.service.whey.WheyProteinService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class LoteServiceImpl implements LoteService {

    @Inject
    public LoteRepository loteRepository;

    @Inject
    public WheyProteinService wheyService;

    @Override
    public Lote findById(Long id) {
        return loteRepository.findById(id);

    }

    @Override
    public List<Lote> findAll() {
        return loteRepository.listAll();
    }

    @Override
    @Transactional
    public Lote create(LoteRequestDTO dto) {
        Lote lote = new Lote();
        lote.setCodigo(generateCode(dto.dataFabricacao()));
        lote.setDataFabricacao(dto.dataFabricacao());
        lote.setQuantidade(dto.quantidade());
        lote.setWheyProtein(wheyService.findById(dto.idWhey()));
        loteRepository.persist(lote);
        return lote;
    }

    @Override
    @Transactional
    public void update(Long id, LoteRequestDTO dto) {
        Lote lote = loteRepository.findById(id);
        if (lote == null)
            throw new IllegalArgumentException("Lote não encontrado!");

        lote.setCodigo(generateCode(dto.dataFabricacao()));
        lote.setDataFabricacao(dto.dataFabricacao());
        lote.setQuantidade(dto.quantidade());
        lote.setWheyProtein(wheyService.findById(dto.idWhey()));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Lote lote = loteRepository.findById(id);
        if (lote == null)
            throw new IllegalArgumentException("Lote não encontrado!");
        loteRepository.delete(lote);
    }

    @Override
    public List<Lote> findByCodigo(String codigo) {
        return loteRepository.findByCodigo(codigo);
    }

    private String generateCode(LocalDate dataFabricacao) {
        String data = dataFabricacao.toString();
        return "L" + data + "WHEY";
    }

    @Override
    public List<Lote> findByWhey(Long idWhey) {
        return loteRepository.findByWhey(idWhey);
    }

}

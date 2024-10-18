package br.unitins.tp1.ironforge.service.whey.sabor;

import java.util.List;

import br.unitins.tp1.ironforge.dto.whey.SaborRequestDTO;
import br.unitins.tp1.ironforge.model.whey.Sabor;
import br.unitins.tp1.ironforge.repository.SaborRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class SaborServiceImpl implements SaborService {

    @Inject
    public SaborRepository saborRepository;

    @Override
    public Sabor findById(Long id) {
        return saborRepository.findById(id);

    }

    @Override
    public List<Sabor> findByNome(String nome) {
        return saborRepository.findByNome(nome);
    }

    @Override
    public List<Sabor> findAll() {
        return saborRepository.listAll();
    }

    @Override
    @Transactional
    public Sabor create(SaborRequestDTO dto) {
        Sabor sabor = new Sabor();
        sabor.setNome(dto.nome());
        saborRepository.persist(sabor);
        return sabor;
    }

    @Override
    @Transactional
    public void update(Long id, SaborRequestDTO dto) {
        Sabor sabor = saborRepository.findById(id);
        if (sabor == null)
            throw new IllegalArgumentException("Sabor não encontrado!");

        sabor.setNome(dto.nome());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Sabor sabor = saborRepository.findById(id);
        if (sabor == null)
            throw new IllegalArgumentException("Sabor não encontrado!");
        saborRepository.delete(sabor);
    }

}

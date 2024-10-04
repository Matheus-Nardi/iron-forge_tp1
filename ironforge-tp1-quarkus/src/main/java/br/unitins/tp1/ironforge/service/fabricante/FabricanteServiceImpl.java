package br.unitins.tp1.ironforge.service.fabricante;

import java.util.List;

import br.unitins.tp1.ironforge.dto.fabricante.FabricanteRequestDTO;
import br.unitins.tp1.ironforge.model.Fabricante;
import br.unitins.tp1.ironforge.repository.FabricanteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class FabricanteServiceImpl implements FabricanteService {

    @Inject
    public FabricanteRepository fabricanteRepository;

    @Override
    public Fabricante findById(Long id) {
        return fabricanteRepository.findById(id);

    }

    @Override
    public List<Fabricante> findByNome(String nome) {
        return fabricanteRepository.findByNome(nome);
    }

    @Override
    public List<Fabricante> findAll() {
        return fabricanteRepository.listAll();
    }

    @Override
    @Transactional
    public Fabricante create(FabricanteRequestDTO dto) {
        Fabricante fabricante = new Fabricante();
        fabricante.setNome(dto.nome());
        fabricante.setCnpj(dto.cnpj());
        fabricante.setEmail(dto.email());
        fabricanteRepository.persist(fabricante);
        return fabricante;
    }

    @Override
    @Transactional
    public void update(Long id, FabricanteRequestDTO dto) {
        Fabricante fabricante = fabricanteRepository.findById(id);
        if (fabricante == null)
            throw new IllegalArgumentException("Fabricante não encontrado!");

        fabricante.setNome(dto.nome());
        fabricanteRepository.persist(fabricante);
        ;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Fabricante fabricante = fabricanteRepository.findById(id);
        if (fabricante == null)
            throw new IllegalArgumentException("Fabricante não encontrado!");
        fabricanteRepository.delete(fabricante);
    }

}

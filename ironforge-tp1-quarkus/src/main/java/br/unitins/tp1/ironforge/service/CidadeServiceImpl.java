package br.unitins.tp1.ironforge.service;

import java.util.List;

import br.unitins.tp1.ironforge.dto.cidade.CidadeRequestDTO;
import br.unitins.tp1.ironforge.model.Cidade;
import br.unitins.tp1.ironforge.repository.CidadeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CidadeServiceImpl implements CidadeService {

    @Inject
    public CidadeRepository cidadeRepository;

    @Inject
    public EstadoService estadoService;

    @Override
    public Cidade findById(Long id) {
        return cidadeRepository.findById(id);
    }

    @Override
    public List<Cidade> findByNome(String nome) {
        return cidadeRepository.findByNome(nome);
    }

    @Override
    @Transactional
    public Cidade create(CidadeRequestDTO dto) {
        Cidade cidade = new Cidade();
        cidade.setNome(dto.nome());
        cidade.setEstado(estadoService.findById(dto.idEstado()));
        cidadeRepository.persist(cidade);
        return cidade;
    }

    @Override
    @Transactional
    public Cidade update(Long id, CidadeRequestDTO dto) {
        Cidade c = cidadeRepository.findById(id);
        c.setNome(dto.nome());
        c.setEstado(estadoService.findById(dto.idEstado()));
        return c;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        cidadeRepository.deleteById(id);
    }

    @Override
    public List<Cidade> findAll() {
        return cidadeRepository.findAll().list();
    }

}

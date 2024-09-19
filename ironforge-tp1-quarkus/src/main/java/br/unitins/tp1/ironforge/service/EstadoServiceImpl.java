package br.unitins.tp1.ironforge.service;

import java.util.List;

import br.unitins.tp1.ironforge.model.Estado;
import br.unitins.tp1.ironforge.repository.EstadoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;


@ApplicationScoped
public class EstadoServiceImpl implements EstadoService {

    @Inject
    public EstadoRepository estadoRepository;

    @Override
    public Estado findById(Long id) {
        return estadoRepository.findById(id);
    }

    @Override
    public List<Estado> findByNome(String nome) {
        return estadoRepository.findByNome(nome);
    }

    @Override
    @Transactional
    public Estado create(Estado estado) {
        estadoRepository.persist(estado);
        return estado;
    }

    @Override
    @Transactional
    public Estado update(Estado estado) {
        Estado e = estadoRepository.findById(estado.getId());
        e.setNome(estado.getNome());
        e.setSigla(estado.getSigla());
        return e;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        estadoRepository.deleteById(id);
    }

    @Override
    public List<Estado> findAll() {
        return estadoRepository.findAll().list();
    }

}

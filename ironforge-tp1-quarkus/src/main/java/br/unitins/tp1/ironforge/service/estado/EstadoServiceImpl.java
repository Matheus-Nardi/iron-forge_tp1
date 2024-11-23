package br.unitins.tp1.ironforge.service.estado;

import java.util.List;

import br.unitins.tp1.ironforge.dto.estado.EstadoRequestDTO;
import br.unitins.tp1.ironforge.model.Estado;
import br.unitins.tp1.ironforge.repository.EstadoRepository;
import br.unitins.tp1.ironforge.validation.EntidadeNotFoundException;
import br.unitins.tp1.ironforge.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class EstadoServiceImpl implements EstadoService {

    @Inject
    public EstadoRepository estadoRepository;

    @Override
    public Estado findById(Long id) {
        Estado estado = estadoRepository.findById(id);
        if (estado == null) {
            throw new EntidadeNotFoundException("id", "Estado não encontrado");
        }
        return estado;
    }

    @Override
    public List<Estado> findByNome(String nome) {
        return estadoRepository.findByNome(nome);
    }

    @Override
    @Transactional
    public Estado create(EstadoRequestDTO dto) {

        verificarSigla(dto.sigla());
        Estado estado = new Estado();
        estado.setNome(dto.nome());
        estado.setSigla(dto.sigla());
        estadoRepository.persist(estado);
        return estado;
    }

    private void verificarSigla(String sigla) {
        if (!(estadoRepository.findBySigla(sigla) == null)) {
            throw new ValidationException("sigla", "A sigla já foi utilizada por outro estado.");
        }
    }

    @Override
    @Transactional
    public Estado update(Long id, EstadoRequestDTO dto) {
        Estado estado = estadoRepository.findById(id);
        if (estado == null) {
            throw new EntidadeNotFoundException("id", "Estado não encontrado");
        }
        verificarSigla(dto.sigla());
        estado.setNome(dto.nome());
        estado.setSigla(dto.sigla());
        return estado;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Estado estado = estadoRepository.findById(id);
        if (estado == null) {
            throw new EntidadeNotFoundException("id", "Estado não encontrado");
        }
        estadoRepository.delete(estado);
    }

    @Override
    public List<Estado> findAll() {
        return estadoRepository.findAll().list();
    }

    @Override
    public Estado findBySigla(String sigla) {
        return estadoRepository.findBySigla(sigla);
    }

}

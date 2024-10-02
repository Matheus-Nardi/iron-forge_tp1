package br.unitins.tp1.ironforge.service.estado;

import java.util.List;

import br.unitins.tp1.ironforge.dto.estado.EstadoDTO;
import br.unitins.tp1.ironforge.dto.estado.EstadoResponseDTO;
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
    public EstadoResponseDTO findById(Long id) {
        Estado estado = estadoRepository.findById(id);
        return EstadoResponseDTO.valueOf(estado);
    }

    @Override
    public List<EstadoResponseDTO> findByNome(String nome) {
        List<Estado> estados = estadoRepository.findByNome(nome);
        return estados.stream().map(EstadoResponseDTO::valueOf).toList();
    }

    @Override
    @Transactional
    public EstadoResponseDTO create(EstadoDTO dto) {
        Estado estado = EstadoDTO.valueOf(dto);
        estadoRepository.persist(estado);
        return EstadoResponseDTO.valueOf(estado);
    }

    @Override
    @Transactional
    public EstadoResponseDTO update(Long id, EstadoDTO dto) {
        Estado estado = estadoRepository.findById(id);
        estado.setNome(dto.nome());
        estado.setSigla(dto.sigla());
        return EstadoResponseDTO.valueOf(estado);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        estadoRepository.deleteById(id);
    }

    @Override
    public List<EstadoResponseDTO> findAll() {
        List<Estado> estados = estadoRepository.findAll().list();
        return estados.stream().map(EstadoResponseDTO::valueOf).toList();
    }

}

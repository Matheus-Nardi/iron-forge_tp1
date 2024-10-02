package br.unitins.tp1.ironforge.service.cidade;

import java.util.List;

import br.unitins.tp1.ironforge.dto.cidade.CidadeDTO;
import br.unitins.tp1.ironforge.dto.cidade.CidadeResponseDTO;
import br.unitins.tp1.ironforge.model.Cidade;
import br.unitins.tp1.ironforge.repository.CidadeRepository;
import br.unitins.tp1.ironforge.repository.EstadoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CidadeServiceImpl implements CidadeService {

    @Inject
    public CidadeRepository cidadeRepository;

    @Inject
    public EstadoRepository estadoRepository;

    @Override
    public CidadeResponseDTO findById(Long id) {
        Cidade cidade = cidadeRepository.findById(id);
        return CidadeResponseDTO.valueOf(cidade);
    }

    @Override
    public List<CidadeResponseDTO> findByNome(String nome) {
        List<Cidade> cidades = cidadeRepository.findByNome(nome);
        return cidades.stream().map(CidadeResponseDTO::valueOf).toList();
    }

    @Override
    @Transactional
    public CidadeResponseDTO create(CidadeDTO dto) {
        Cidade cidade = new Cidade();
        cidade.setNome(dto.nome());
        cidade.setEstado(estadoRepository.findById(dto.idEstado()));
        cidadeRepository.persist(cidade);
        return CidadeResponseDTO.valueOf(cidade);
    }

    @Override
    @Transactional
    public CidadeResponseDTO update(Long id, CidadeDTO dto) {
        Cidade cidade = cidadeRepository.findById(id);
        cidade.setNome(dto.nome());
        cidade.setEstado(estadoRepository.findById(dto.idEstado()));
        return CidadeResponseDTO.valueOf(cidade);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        cidadeRepository.deleteById(id);
    }

    @Override
    public List<CidadeResponseDTO> findAll() {
        List<Cidade> cidades = cidadeRepository.findAll().list();
        return cidades.stream().map(CidadeResponseDTO::valueOf).toList();
    }

}

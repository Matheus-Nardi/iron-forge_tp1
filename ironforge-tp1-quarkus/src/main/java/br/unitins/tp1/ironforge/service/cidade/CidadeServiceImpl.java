package br.unitins.tp1.ironforge.service.cidade;

import java.util.List;

import br.unitins.tp1.ironforge.dto.cidade.CidadeRequestDTO;
import br.unitins.tp1.ironforge.model.endereco.Cidade;
import br.unitins.tp1.ironforge.repository.CidadeRepository;
import br.unitins.tp1.ironforge.service.estado.EstadoService;
import br.unitins.tp1.ironforge.validation.EntidadeNotFoundException;
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
        Cidade cidade = findCidade(id);
        return cidade;
    }

    private Cidade findCidade(Long id) {
        Cidade cidade = cidadeRepository.findById(id);
        if (cidade == null)
            throw new EntidadeNotFoundException("id", "Cidade não encontrada");

        return cidade;
    }

    @Override
    public List<Cidade> findByNome(String nome) {
        List<Cidade> cidades = cidadeRepository.findByNome(nome);
        return cidades;
    }

    @Override
    @Transactional
    public Cidade create(CidadeRequestDTO dto) {
        Cidade cidade = new Cidade();
        cidade.setNome(dto.nome());
        if (estadoService.findById(dto.idEstado()) == null) {
            throw new EntidadeNotFoundException("idEstado", "Estado não encontrado");
        }
        cidade.setEstado(estadoService.findById(dto.idEstado()));
        cidadeRepository.persist(cidade);
        return cidade;
    }

    @Override
    @Transactional
    public Cidade update(Long id, CidadeRequestDTO dto) {
        Cidade cidade = findCidade(id);
        cidade.setNome(dto.nome());
        if (estadoService.findById(dto.idEstado()) == null) {
            throw new EntidadeNotFoundException("idEstado", "Estado não encontrado");
        }
        cidade.setEstado(estadoService.findById(dto.idEstado()));
        return cidade;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Cidade cidade = findCidade(id);
        cidadeRepository.delete(cidade);
    }

    @Override
    public List<Cidade> findAll() {
        List<Cidade> cidades = cidadeRepository.findAll().list();
        return cidades;
    }

}

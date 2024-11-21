package br.unitins.tp1.ironforge.service.avaliacao;

import java.time.LocalDate;
import java.util.List;

import br.unitins.tp1.ironforge.dto.avaliacao.AvaliacaoRequestDTO;
import br.unitins.tp1.ironforge.model.avaliacao.Avaliacao;
import br.unitins.tp1.ironforge.model.avaliacao.Nota;
import br.unitins.tp1.ironforge.model.usuario.Cliente;
import br.unitins.tp1.ironforge.model.whey.WheyProtein;
import br.unitins.tp1.ironforge.repository.AvaliacaoRepository;
import br.unitins.tp1.ironforge.service.usuario.ClienteService;
import br.unitins.tp1.ironforge.service.whey.WheyProteinService;
import br.unitins.tp1.ironforge.validation.EntidadeNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class AvaliacaoServiceImpl implements AvaliacaoService {

    @Inject
    public AvaliacaoRepository avaliacaoRepository;

    @Inject
    public ClienteService clienteService;

    @Inject
    public WheyProteinService wheyService;

    @Override
    public Avaliacao findById(Long id) {
        Avaliacao avaliacao = findAvaliacao(id);
        return avaliacao;
    }

    private Avaliacao findAvaliacao(Long id) {
        Avaliacao avaliacao = avaliacaoRepository.findById(id);
        if (avaliacao == null) {
            throw new EntidadeNotFoundException("id", "Avaliação não encontrada");
        }
        return avaliacao;
    }

    @Override
    public List<Avaliacao> findByWhey(Long idWhey) {
        return avaliacaoRepository.findByWhey(idWhey);
    }

    @Override
    public List<Avaliacao> findAll(String username) {
        return avaliacaoRepository.findByCliente(clienteService.findByUsuario(username).getId());
    }

    @Override
    @Transactional
    public Avaliacao create(AvaliacaoRequestDTO dto, String username) {
        Cliente cliente = findCliente(username);
        WheyProtein wheyProtein = findWheyProtein(dto);
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setComentario(dto.comentario());
        avaliacao.setNota(Nota.valueOf(dto.nota()));
        avaliacao.setData(LocalDate.now());
        avaliacao.setCliente(cliente);
        avaliacao.setWheyProtein(wheyProtein);

        avaliacaoRepository.persist(avaliacao);

        return avaliacao;
    }

    private WheyProtein findWheyProtein(AvaliacaoRequestDTO dto) {
        WheyProtein wheyProtein = wheyService.findById(dto.idWhey());
        if (wheyProtein == null) {
            throw new EntidadeNotFoundException("idWhey", "Whey Protein não encontrado");
        }
        return wheyProtein;
    }

    private Cliente findCliente(String username) {
        Cliente cliente = clienteService.findByUsuario(username);
        if (cliente == null) {
            throw new EntidadeNotFoundException("username", "Cliente não encontrado");
        }
        return cliente;
    }

    @Override
    @Transactional
    public void update(Long id, AvaliacaoRequestDTO dto, String username) {
        Cliente cliente = findCliente(username);
        WheyProtein wheyProtein = findWheyProtein(dto);
        Avaliacao avaliacao = findAvaliacao(id);
        avaliacao.setComentario(dto.comentario());
        avaliacao.setNota(Nota.valueOf(dto.nota()));
        avaliacao.setData(LocalDate.now());
        avaliacao.setCliente(cliente);
        avaliacao.setWheyProtein(wheyProtein);

    }

    @Override
    public void delete(Long id) {
        Avaliacao avaliacao = findAvaliacao(id);
        avaliacaoRepository.delete(avaliacao);
    }

}

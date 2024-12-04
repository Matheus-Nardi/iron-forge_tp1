package br.unitins.tp1.ironforge.service.cupom;

import java.util.List;

import br.unitins.tp1.ironforge.dto.cupom.CupomRequestDTO;
import br.unitins.tp1.ironforge.model.pedido.Cupom;
import br.unitins.tp1.ironforge.model.usuario.Fabricante;
import br.unitins.tp1.ironforge.repository.CupomRepository;
import br.unitins.tp1.ironforge.service.fabricante.FabricanteService;
import br.unitins.tp1.ironforge.validation.EntidadeNotFoundException;
import br.unitins.tp1.ironforge.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CupomServiceImpl implements CupomService {

    @Inject
    public CupomRepository cupomRepository;

    @Inject
    public FabricanteService fabricanteService;

    @Override
    public Cupom findById(Long id) {
        Cupom cupom = cupomRepository.findById(id);
        if (cupom == null) {
            throw new EntidadeNotFoundException("id", "Cupom não encontrado");
        }

        return cupom;

    }

    @Override
    public List<Cupom> findAll() {
        return cupomRepository.listAll();
    }

    @Override
    @Transactional
    public Cupom create(CupomRequestDTO dto) {
        Cupom cupom = new Cupom();
        cupom.setCodigo(dto.codigo());
        cupom.setPercentualDesconto(dto.percentualDesconto());
        cupom.setDataValidade(dto.dataValidade());
        Fabricante fabricante = fabricanteService.findById(dto.idFabricante());
        if (fabricante == null) {
            throw new EntidadeNotFoundException("idFabricante", "Fabricante não encontrado");
        }
        cupom.setFabricante(fabricante);
        cupom.setAtivo(dto.ativo());
        cupomRepository.persist(cupom);
        return cupom;
    }

    @Override
    @Transactional
    public void update(Long id, CupomRequestDTO dto) {
        Cupom cupom = cupomRepository.findById(id);
        if (cupom == null)
            throw new EntidadeNotFoundException("id", "Cupom não encontrado!");

        cupom.setCodigo(dto.codigo());
        cupom.setPercentualDesconto(dto.percentualDesconto());
        cupom.setDataValidade(dto.dataValidade());
        Fabricante fabricante = fabricanteService.findById(dto.idFabricante());
        if (fabricante == null) {
            throw new EntidadeNotFoundException("idFabricante", "Fabricante não encontrado");
        }
        cupom.setFabricante(fabricante);
        cupom.setAtivo(dto.ativo());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Cupom cupom = cupomRepository.findById(id);
        if (cupom == null)
            throw new EntidadeNotFoundException("id", "Cupom não encontrado!");
        cupomRepository.delete(cupom);
    }

    @Override
    public List<Cupom> findByCodigo(String codigo) {
        return cupomRepository.findByCodigo(codigo);
    }

    @Override
    public List<Cupom> findByFabricante(Long idFabricante) {
        Fabricante fabricante = fabricanteService.findById(idFabricante);
        if (fabricante == null) {
            throw new EntidadeNotFoundException("idFabricante", "Fabricante não encontrado");
        }
        return cupomRepository.findByFabricante(idFabricante);
    }

    @Transactional
    @Override
    public void deactivate(Long id) {
        Cupom cupom = cupomRepository.findById(id);

        if (!cupom.getAtivo())
            throw new ValidationException("id", "Cupom já esta desativado");

        cupom.setAtivo(false);
    }

}

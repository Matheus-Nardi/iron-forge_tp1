package br.unitins.tp1.ironforge.service.cupom;

import java.util.List;

import br.unitins.tp1.ironforge.dto.cupom.CupomRequestDTO;
import br.unitins.tp1.ironforge.infra.exception.NotFoundException;
import br.unitins.tp1.ironforge.model.Cupom;
import br.unitins.tp1.ironforge.repository.CupomRepository;
import br.unitins.tp1.ironforge.service.fabricante.FabricanteService;
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
        if (cupom == null)
            throw new NotFoundException("Cupom não encontrado");
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
        cupom.setFabricante(fabricanteService.findById(dto.idFabricante()));
        cupomRepository.persist(cupom);
        return cupom;
    }

    @Override
    @Transactional
    public void update(Long id, CupomRequestDTO dto) {
        Cupom cupom = cupomRepository.findById(id);
        if (cupom == null)
            throw new IllegalArgumentException("Cupom não encontrado!");

        cupom.setCodigo(dto.codigo());
        cupom.setPercentualDesconto(dto.percentualDesconto());
        cupom.setDataValidade(dto.dataValidade());
        cupom.setFabricante(fabricanteService.findById(dto.idFabricante()));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Cupom cupom = cupomRepository.findById(id);
        if (cupom == null)
            throw new IllegalArgumentException("Cupom não encontrado!");
        cupomRepository.delete(cupom);
    }

    @Override
    public List<Cupom> findByCodigo(String codigo) {
        return cupomRepository.findByCodigo(codigo);
    }

    @Override
    public List<Cupom> findByFabricante(Long idFabricante) {
        return cupomRepository.findByFabricante(idFabricante);
    }

    @Transactional
    @Override
    public void deactivate(Long id) {
        Cupom cupom = cupomRepository.findById(id);

        if (!cupom.getAtivo())
            throw new IllegalArgumentException("Cupom já esta desativado");

        cupom.setAtivo(false);
    }

}

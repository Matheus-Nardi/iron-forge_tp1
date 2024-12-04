package br.unitins.tp1.ironforge.service.cupom;

import java.util.List;

import br.unitins.tp1.ironforge.dto.cupom.CupomRequestDTO;
import br.unitins.tp1.ironforge.model.pedido.Cupom;

public interface CupomService {

    Cupom findById(Long id);

    List<Cupom> findByCodigo(String codigo);

    List<Cupom> findByFabricante(Long idFabricante);

    List<Cupom> findAll();

    void deactivate(Long id);

    Cupom create(CupomRequestDTO dto);

    void update(Long id, CupomRequestDTO dto);

    void delete(Long id);

}

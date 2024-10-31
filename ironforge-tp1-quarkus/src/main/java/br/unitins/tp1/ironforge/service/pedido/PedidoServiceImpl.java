package br.unitins.tp1.ironforge.service.pedido;

import java.util.List;

import br.unitins.tp1.ironforge.dto.pedido.PedidoRequestDTO;
import br.unitins.tp1.ironforge.model.Pedido;
import br.unitins.tp1.ironforge.repository.PedidoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class PedidoServiceImpl implements PedidoService {

    @Inject
    public PedidoRepository pedidoRepository;


    @Override
    public Pedido findById(Long id) {
        return pedidoRepository.findById(id);
    }

    @Override
    @Transactional
    public Pedido create(PedidoRequestDTO dto) {
       
        return null;
    }

    @Override
    public List<Pedido> findByUsername(String username) {
        return null;
    }

}

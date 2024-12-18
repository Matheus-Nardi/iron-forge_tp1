package br.unitins.tp1.ironforge.service.cartao;

import java.util.ArrayList;
import java.util.List;

import br.unitins.tp1.ironforge.dto.pagamento.CartaoRequestDTO;
import br.unitins.tp1.ironforge.model.pagamento.Cartao;
import br.unitins.tp1.ironforge.model.usuario.Cliente;
import br.unitins.tp1.ironforge.repository.CartaoRepository;
import br.unitins.tp1.ironforge.service.hash.HashService;
import br.unitins.tp1.ironforge.service.usuario.ClienteService;
import br.unitins.tp1.ironforge.validation.EntidadeNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CartaoServiceImpl implements CartaoService {

    @Inject
    public CartaoRepository cartaoRepository;

    @Inject
    public ClienteService clienteService;

    @Inject
    public HashService hashService;

    @Override
    public Cartao findById(Long id) {
        Cartao cartao = cartaoRepository.findById(id);
        if (cartao == null)
            throw new EntidadeNotFoundException("id", "Cartao não encontrado!");

        return cartao;

    }

    @Override
    public List<Cartao> findByCliente(String username) {
        return clienteService.findByUsuario(username).getCartoes();
    }

    @Override
    public List<Cartao> findAll() {
        return cartaoRepository.listAll();
    }

    @Override
    @Transactional
    public Cartao create(String username, CartaoRequestDTO dto) {
        Cliente cliente = clienteService.findByUsuario(username);
        Cartao cartao = new Cartao();
        cartao.setCpf(dto.cpf());
        cartao.setTitular(dto.titular());
        cartao.setNumero(mascararNumeroCartao(dto.numero()));
        cartao.setCvc(hashService.getHashSenha(dto.cvc()));
        cartao.setValidade(dto.validade());
        cartao.setTipoCartao(dto.tipoCartao());
        cartaoRepository.persist(cartao);
        addCartaoCliente(cliente, cartao);

        return cartao;
    }

    private void addCartaoCliente(Cliente cliente, Cartao cartao) {
        if (cliente.getCartoes() == null) {
            cliente.setCartoes(new ArrayList<>());
        }

        cliente.getCartoes().add(cartao);

    }

    @Override
    @Transactional
    public void update(String username, Long id, CartaoRequestDTO dto) {
        Cliente cliente = clienteService.findByUsuario(username);
        Cartao cartao = cartaoRepository.findById(id);
        if (!cliente.getCartoes().contains(cartao)) {
            throw new EntidadeNotFoundException("id", "Cartão não encontrado");
        }
        cartao.setCpf(dto.cpf());
        cartao.setTitular(dto.titular());
        cartao.setNumero(dto.numero());
        cartao.setCvc(dto.cvc());
        cartao.setValidade(dto.validade());
        cartao.setTipoCartao(dto.tipoCartao());
    }

    @Override
    @Transactional
    public void delete(String username, Long id) {
        Cliente cliente = clienteService.findByUsuario(username);
        Cartao cartao = cartaoRepository.findById(id);
        if (!cliente.getCartoes().contains(cartao)) {
            throw new EntidadeNotFoundException("id", "Cartão não encontrado");
        }
        cliente.getCartoes().remove(cartao);
    }

    private static String mascararNumeroCartao(String numero) {
        if (numero == null || numero.length() < 4) {
            return "****";
        }
        int length = numero.length();

        StringBuilder mascarado = new StringBuilder();
        for (int i = 0; i < length - 4; i++) {
            if (i > 0 && i % 4 == 0) {
                mascarado.append(" ");
            }
            mascarado.append("*");
        }

        mascarado.append(" ").append(numero.substring(length - 4));
        return mascarado.toString().trim();
    }

}

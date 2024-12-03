package br.unitins.tp1.ironforge.repository;

import java.util.List;

import br.unitins.tp1.ironforge.model.usuario.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente> {

    public List<Cliente> findClienteByNome(String nome) {
        return find("pessoaFisica.nome LIKE ?1", "%" + nome + "%").list();
    }

    public Cliente findClienteByUsername(String username) {
        return find("pessoaFisica.usuario.username = ?1 ", username).firstResult();
    }

    public Cliente findClienteByCpf(String cpf) {
        return find("pessoaFisica.cpf = ?1 ", cpf).firstResult();
    }

    public Cliente findClienteByEmail(String email) {
        return find("pessoaFisica.email = ?1 ", email).firstResult();
    }

    public Cliente existsByPessoaFisica(Long idPessoaFisica) {
        return find("pessoaFisica.id = ?1", idPessoaFisica).firstResult();
    }

}

package br.unitins.tp1.ironforge.service.usuario;

import java.util.List;

import br.unitins.tp1.ironforge.dto.endereco.EnderecoRequestDTO;
import br.unitins.tp1.ironforge.dto.pessoafisica.ClienteRequestDTO;
import br.unitins.tp1.ironforge.dto.pessoafisica.ClienteUpdateRequestDTO;
import br.unitins.tp1.ironforge.dto.telefone.TelefoneRequestDTO;
import br.unitins.tp1.ironforge.model.endereco.Endereco;
import br.unitins.tp1.ironforge.model.usuario.Cliente;
import br.unitins.tp1.ironforge.model.usuario.Telefone;
import br.unitins.tp1.ironforge.model.whey.WheyProtein;

public interface ClienteService {

    Cliente findById(Long id);

    List<Cliente> findByNome(String nome);

    Cliente findByUsuario(String username);

    List<Cliente> findAll();

    Cliente create(String username, ClienteRequestDTO dto);

    void update(String username, ClienteUpdateRequestDTO dto);

    void delete(Long id);

    void updateTelefone(String username, Long idTelefone, TelefoneRequestDTO dto);

    Telefone addTelefone(String username, TelefoneRequestDTO dto);

    void updateEndereco(String username, Long idEndereco, EnderecoRequestDTO dto);

    Endereco addEndereco(String username, EnderecoRequestDTO dto);

    void updateNomeImagem(String username, String nomeImagem);

    void adicionarListaDesejo(String username, Long idProduto);

    void removerListaDesejo(String username, Long idProduto);

    List<WheyProtein> getListaDesejos(String username);

    boolean existsByPessoaFisica(Long idPessoaFisica);

}

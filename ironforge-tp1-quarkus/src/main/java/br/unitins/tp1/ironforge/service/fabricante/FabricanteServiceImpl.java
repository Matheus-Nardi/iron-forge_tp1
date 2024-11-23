package br.unitins.tp1.ironforge.service.fabricante;

import java.util.ArrayList;
import java.util.List;

import br.unitins.tp1.ironforge.dto.endereco.EnderecoRequestDTO;
import br.unitins.tp1.ironforge.dto.pessoajuridica.FabricanteRequestDTO;
import br.unitins.tp1.ironforge.dto.pessoajuridica.FabricanteUpdateRequestDTO;
import br.unitins.tp1.ironforge.dto.telefone.TelefoneRequestDTO;
import br.unitins.tp1.ironforge.model.Endereco;
import br.unitins.tp1.ironforge.model.Fabricante;
import br.unitins.tp1.ironforge.model.Telefone;
import br.unitins.tp1.ironforge.model.usuario.PessoaJuridica;
import br.unitins.tp1.ironforge.model.usuario.Usuario;
import br.unitins.tp1.ironforge.repository.FabricanteRepository;
import br.unitins.tp1.ironforge.repository.PessoaJuridicaRepository;
import br.unitins.tp1.ironforge.repository.UsuarioRepository;
import br.unitins.tp1.ironforge.service.cidade.CidadeService;
import br.unitins.tp1.ironforge.validation.EntidadeNotFoundException;
import br.unitins.tp1.ironforge.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class FabricanteServiceImpl implements FabricanteService {

    @Inject
    public FabricanteRepository fabricanteRepository;

    @Inject
    public PessoaJuridicaRepository pessoaJuridicaRepository;

    @Inject
    public UsuarioRepository usuarioRepository;

    @Inject
    public CidadeService cidadeService;

    @Override
    public Fabricante findById(Long id) {
        Fabricante fabricante = fabricanteRepository.findById(id);

        if (fabricante == null) {
            throw new EntidadeNotFoundException("id", "Fabricante não encontrado");
        }
        return fabricante;

    }

    @Override
    public List<Fabricante> findByNome(String nome) {
        return fabricanteRepository.findByNome(nome);
    }

    @Override
    public List<Fabricante> findAll() {
        return fabricanteRepository.listAll();
    }

    @Override
    @Transactional
    public Fabricante create(FabricanteRequestDTO dto) {

        validarEntidade(dto);

        Fabricante fabricante = new Fabricante();
        Usuario usuario = getUsuario(dto);

        PessoaJuridica pessoaJuridica = getPessoaJuridica(dto, usuario);
        pessoaJuridicaRepository.persist(pessoaJuridica);
        fabricante.setPessoaJuridica(pessoaJuridica);
        fabricanteRepository.persist(fabricante);

        return fabricante;
    }

    private void validarEntidade(FabricanteRequestDTO dto) {
        if (existeUsuario(dto.usuario().username())) {
            throw new ValidationException("usuario.username", "Username inválido");
        }

        if (existeCNPJ(dto.cnpj())) {
            throw new ValidationException("cnpj", "Cnpj informado é inválido");
        }
        if (existeEmail(dto.email())) {
            throw new ValidationException("email", "Email informado é inválido");
        }
    }

    private PessoaJuridica getPessoaJuridica(FabricanteRequestDTO dto, Usuario usuario) {
        PessoaJuridica pessoaJuridica = new PessoaJuridica();
        pessoaJuridica.setUsuario(usuario); // Associando pessoa com usuario
        // Defenindo pessoa juridica
        pessoaJuridica.setNome(dto.nome());
        pessoaJuridica.setEmail(dto.email());
        pessoaJuridica.setCnpj(dto.cnpj());
        pessoaJuridica.setEnderecos(getEnderecos(dto));
        pessoaJuridica.setTelefones(getTelefones(dto));
        return pessoaJuridica;
    }

    private Usuario getUsuario(FabricanteRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setUsername(dto.usuario().username());
        usuario.setSenha(dto.usuario().senha());
        usuario.setPerfil(dto.usuario().perfil());
        usuarioRepository.persist(usuario);
        return usuario;
    }

    @Override
    @Transactional
    public void update(Long id, FabricanteUpdateRequestDTO dto) {
        Fabricante fabricante = fabricanteRepository.findById(id);

        if (fabricante == null)
            throw new EntidadeNotFoundException("id", "Fabricante não encontrado");

        if (existeCNPJ(dto.cnpj())) {
            throw new ValidationException("cnpj", "Cnpj informado é inválido");
        }

        if (existeEmail(dto.email())) {
            throw new ValidationException("email", "Email informado é inválido");
        }

        PessoaJuridica pJ = fabricante.getPessoaJuridica();
        pJ.setNome(dto.nome());
        pJ.setCnpj(dto.cnpj());
        pJ.setEmail(dto.email());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Fabricante fabricante = fabricanteRepository.findById(id);
        if (fabricante == null) {
            throw new EntidadeNotFoundException("fabricanteId", "Fabricante não encontrado");
        }
        usuarioRepository.delete(fabricante.getPessoaJuridica().getUsuario());
        pessoaJuridicaRepository.delete(fabricante.getPessoaJuridica());
        fabricanteRepository.delete(fabricante);
    }

    private List<Telefone> getTelefones(FabricanteRequestDTO dto) {
        List<Telefone> telefones = new ArrayList<>();

        for (int i = 0; i < dto.telefones().size(); i++) {
            Telefone telefone = new Telefone();
            TelefoneRequestDTO telefoneRequestDTO = dto.telefones().get(i);
            telefone.setCodigoArea(telefoneRequestDTO.codigoArea());
            telefone.setNumero(telefoneRequestDTO.numero());
            telefones.add(telefone);
        }
        return telefones;
    }

    private List<Endereco> getEnderecos(FabricanteRequestDTO dto) {
        List<Endereco> enderecos = new ArrayList<>();

        for (int i = 0; i < dto.enderecos().size(); i++) {
            Endereco endereco = new Endereco();
            EnderecoRequestDTO enderecoRequestDTO = dto.enderecos().get(i);
            endereco.setBairro(enderecoRequestDTO.bairro());
            endereco.setCep(enderecoRequestDTO.cep());
            endereco.setComplemento(enderecoRequestDTO.complemento());
            endereco.setLogradouro(enderecoRequestDTO.logradouro());
            endereco.setNumero(enderecoRequestDTO.numero());
            endereco.setCidade(cidadeService.findById(enderecoRequestDTO.idCidade()));
            enderecos.add(endereco);
        }
        return enderecos;
    }

    @Override
    @Transactional
    public void updateTelefone(Long id, Long idTelefone, TelefoneRequestDTO dto) {
        Fabricante fabricante = fabricanteRepository.findById(id);

        if (fabricante == null) {
            throw new EntidadeNotFoundException("id", "Fabricante não encontrado");
        }
        Telefone telefone = fabricante.getPessoaJuridica().getTelefones().stream()
                .filter(t -> t.getId().equals(idTelefone)).findFirst()
                .orElseThrow(() -> new EntidadeNotFoundException("idTelefone", "Telefone não encontrado"));
        telefone.setCodigoArea(dto.codigoArea());
        telefone.setNumero(dto.numero());
    }

    @Override
    @Transactional
    public void updateEndereco(Long id, Long idEndereco, EnderecoRequestDTO dto) {
        Fabricante fabricante = fabricanteRepository.findById(id);

        if (fabricante == null) {
            throw new EntidadeNotFoundException("id", "Fabricante não encontrado");
        }
        Endereco endereco = fabricante.getPessoaJuridica().getEnderecos().stream()
                .filter(e -> e.getId().equals(idEndereco)).findFirst()
                .orElseThrow(() -> new EntidadeNotFoundException("idEndereco", "Endereco não encontrado"));

        endereco.setBairro(dto.bairro());
        endereco.setCep(dto.cep());
        endereco.setComplemento(dto.complemento());
        endereco.setLogradouro(dto.logradouro());
        endereco.setNumero(dto.numero());
        endereco.setCidade(cidadeService.findById(dto.idCidade()));

    }

    private boolean existeUsuario(String username) {
        return fabricanteRepository.findFabricanteByUsername(username) == null ? false : true;
    }

    private boolean existeCNPJ(String cnpj) {
        return fabricanteRepository.findFabricanteByCnpj(cnpj) == null ? false : true;
    }

    private boolean existeEmail(String email) {
        return fabricanteRepository.findFabricanteByEmail(email) == null ? false : true;
    }

}

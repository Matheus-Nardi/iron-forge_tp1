package br.unitins.tp1.ironforge.service.fabricante;

import java.util.ArrayList;
import java.util.List;

import br.unitins.tp1.ironforge.dto.endereco.EnderecoRequestDTO;
import br.unitins.tp1.ironforge.dto.fabricante.FabricanteCreateRequestDTO;
import br.unitins.tp1.ironforge.dto.fabricante.FabricanteUpdateRequestDTO;
import br.unitins.tp1.ironforge.dto.telefone.TelefoneRequestDTO;
import br.unitins.tp1.ironforge.infra.exception.NotFoundException;
import br.unitins.tp1.ironforge.model.Endereco;
import br.unitins.tp1.ironforge.model.Fabricante;
import br.unitins.tp1.ironforge.model.Telefone;
import br.unitins.tp1.ironforge.repository.FabricanteRepository;
import br.unitins.tp1.ironforge.service.cidade.CidadeService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class FabricanteServiceImpl implements FabricanteService {

    @Inject
    public FabricanteRepository fabricanteRepository;

    @Inject
    public CidadeService cidadeService;

    @Override
    public Fabricante findById(Long id) {
        Fabricante fabricante = fabricanteRepository.findById(id);
        if (fabricante == null)
            throw new NotFoundException("Fabricante não encontrado!");
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
    public Fabricante create(FabricanteCreateRequestDTO dto) {
        Fabricante fabricante = new Fabricante();
        fabricante.setNome(dto.nome());
        fabricante.setCnpj(dto.cnpj());
        fabricante.setEmail(dto.email());
        fabricante.setTelefones(getTelefones(dto));
        fabricante.setEnderecos(getEnderecos(dto));
        fabricanteRepository.persist(fabricante);
        return fabricante;
    }

    @Override
    @Transactional
    public void update(Long id, FabricanteUpdateRequestDTO dto) {
        Fabricante fabricante = fabricanteRepository.findById(id);
        if (fabricante == null)
            throw new NotFoundException("Fabricante não encontrado!");

        fabricante.setNome(dto.nome());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Fabricante fabricante = fabricanteRepository.findById(id);
        if (fabricante == null)
            throw new NotFoundException("Fabricante não encontrado!");
        fabricanteRepository.delete(fabricante);
    }

    private List<Telefone> getTelefones(FabricanteCreateRequestDTO dto) {
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

    private List<Endereco> getEnderecos(FabricanteCreateRequestDTO dto) {
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
        if (fabricante == null)
            throw new NotFoundException("Fabricante não encontrado!");
        Telefone telefone = fabricante.getTelefones().stream().filter(t -> t.getId().equals(idTelefone)).findFirst()
                .orElseThrow(() -> new NotFoundException("Telefone não encontrado"));
        telefone.setCodigoArea(dto.codigoArea());
        telefone.setNumero(dto.numero());
    }

    @Override
    @Transactional
    public void updateEndereco(Long id, Long idEndereco, EnderecoRequestDTO dto) {
        Fabricante fabricante = fabricanteRepository.findById(id);
        if (fabricante == null)
            throw new NotFoundException("Fabricante não encontrado!");
        Endereco endereco = fabricante.getEnderecos().stream().filter(e -> e.getId().equals(idEndereco)).findFirst()
                .orElseThrow(() -> new NotFoundException("Endereco não encontrado"));

        endereco.setBairro(dto.bairro());
        endereco.setCep(dto.cep());
        endereco.setComplemento(dto.complemento());
        endereco.setLogradouro(dto.logradouro());
        endereco.setNumero(dto.numero());
        endereco.setCidade(cidadeService.findById(dto.idCidade()));

    }

}

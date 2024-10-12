package br.unitins.tp1.ironforge.service.fabricante;

import java.util.ArrayList;
import java.util.List;

import br.unitins.tp1.ironforge.dto.endereco.EnderecoRequestDTO;
import br.unitins.tp1.ironforge.dto.fabricante.FabricanteRequestDTO;
import br.unitins.tp1.ironforge.dto.usuario.TelefoneRequestDTO;
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
        return fabricanteRepository.findById(id);

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
    public void update(Long id, FabricanteRequestDTO dto) {
        Fabricante fabricante = fabricanteRepository.findById(id);
        if (fabricante == null)
            throw new IllegalArgumentException("Fabricante não encontrado!");

        fabricante.setNome(dto.nome());
        fabricanteRepository.persist(fabricante);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Fabricante fabricante = fabricanteRepository.findById(id);
        if (fabricante == null)
            throw new IllegalArgumentException("Fabricante não encontrado!");
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

}

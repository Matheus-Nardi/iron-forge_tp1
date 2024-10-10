package br.unitins.tp1.ironforge.service.usuario;

import java.util.List;

import br.unitins.tp1.ironforge.dto.usuario.pessoa.PessoaJuridicaRequestDTO;
import br.unitins.tp1.ironforge.model.usuario.PessoaJuridica;
import br.unitins.tp1.ironforge.repository.PessoaJuridicaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class PessoaJuridicaServiceImpl implements PessoaJuridicaService {

    @Inject
    public PessoaJuridicaRepository pessoajuridicaRepository;

    @Override
    public PessoaJuridica findById(Long id) {
        return pessoajuridicaRepository.findById(id);
    }

    @Override
    public List<PessoaJuridica> findByNome(String nome) {
        return pessoajuridicaRepository.findByNome(nome);
    }

    @Override
    @Transactional
    public PessoaJuridica create(PessoaJuridicaRequestDTO dto) {
        PessoaJuridica pessoaJuridica = new PessoaJuridica();
        pessoaJuridica.setNome(dto.nome());
        pessoaJuridica.setCnpj(dto.cnpj());
        pessoajuridicaRepository.persist(pessoaJuridica);
        return pessoaJuridica;
    }

    @Override
    @Transactional
    public PessoaJuridica update(Long id, PessoaJuridicaRequestDTO dto) {
        PessoaJuridica pessoajuridica = pessoajuridicaRepository.findById(id);
        pessoajuridica.setNome(dto.nome());
        pessoajuridica.setCnpj(dto.cnpj());
        return pessoajuridica;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        pessoajuridicaRepository.deleteById(id);
    }

    @Override
    public List<PessoaJuridica> findAll() {
        return pessoajuridicaRepository.findAll().list();
    }

}

package br.unitins.tp1.ironforge.service.usuario;

import java.util.List;

import br.unitins.tp1.ironforge.dto.usuario.pessoa.PessoaJuridicaRequestDTO;
import br.unitins.tp1.ironforge.model.usuario.PessoaJuridica;

public interface PessoaJuridicaService {

    PessoaJuridica findById(Long id);

    List<PessoaJuridica> findByNome(String nome);

    List<PessoaJuridica> findAll();

    PessoaJuridica create(PessoaJuridicaRequestDTO dto);

    PessoaJuridica update(Long id, PessoaJuridicaRequestDTO dto);

    void delete(Long id);

}

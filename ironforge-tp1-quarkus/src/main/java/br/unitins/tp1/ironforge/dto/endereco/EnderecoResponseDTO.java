package br.unitins.tp1.ironforge.dto.endereco;

import br.unitins.tp1.ironforge.dto.cidade.CidadeResponseDTO;
import br.unitins.tp1.ironforge.model.Endereco;
import br.unitins.tp1.ironforge.model.pedido.EnderecoEntrega;

public record EnderecoResponseDTO(
        Long id,
        CidadeResponseDTO cidade,
        String logradouro,
        String bairro,
        String numero,
        String complemento,
        String cep) {
    public static  EnderecoResponseDTO valueOf(Endereco endereco){
        return new EnderecoResponseDTO(endereco.getId(), CidadeResponseDTO.valueOf(endereco.getCidade()), endereco.getLogradouro(), endereco.getBairro(),  endereco.getNumero(), endereco.getComplemento(), endereco.getCep());
    }
    public static  EnderecoResponseDTO valueOf(EnderecoEntrega enderecoEntrega){
        return new EnderecoResponseDTO(enderecoEntrega.getId(), CidadeResponseDTO.valueOf(enderecoEntrega.getCidade()), enderecoEntrega.getLogradouro(), enderecoEntrega.getBairro(),  enderecoEntrega.getNumero(), enderecoEntrega.getComplemento(), enderecoEntrega.getCep());
    }

}

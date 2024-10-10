package br.unitins.tp1.ironforge.service.usuario;

import br.unitins.tp1.ironforge.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    public UsuarioRepository usuarioRepository;

    @Override
    public void validateCredentials(String cpf, String email) {

        if (usuarioRepository.existByCpf(cpf))
            throw new IllegalArgumentException("Já existe um usuário cadastrado com esse CPF");

        if (usuarioRepository.existByEmail(email))
            throw new IllegalArgumentException("Já existe um usuário cadastrado com esse email");
    }

}

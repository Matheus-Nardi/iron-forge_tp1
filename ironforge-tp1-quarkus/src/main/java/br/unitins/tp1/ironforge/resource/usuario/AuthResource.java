package br.unitins.tp1.ironforge.resource.usuario;

import br.unitins.tp1.ironforge.dto.usuario.AuthFuncionarioRequestDTO;
import br.unitins.tp1.ironforge.dto.usuario.AuthRequestDTO;
import br.unitins.tp1.ironforge.dto.usuario.UsuarioResponseDTO;
import br.unitins.tp1.ironforge.model.usuario.Usuario;
import br.unitins.tp1.ironforge.service.auth.AuthService;
import br.unitins.tp1.ironforge.service.hash.HashService;
import br.unitins.tp1.ironforge.service.jwt.JwtService;
import br.unitins.tp1.ironforge.service.usuario.AdministradorService;
import br.unitins.tp1.ironforge.service.usuario.UsuarioService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    HashService hashService;

    @Inject
    UsuarioService usuarioService;

    @Inject
    public AdministradorService administradorService;

    @Inject
    public AuthService authService;

    @Inject
    JwtService jwtService;

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(@Valid AuthRequestDTO authDTO) {
        String hash = hashService.getHashSenha(authDTO.senha());

        Usuario usuario = usuarioService.findByUsernameAndSenha(authDTO.username(), hash);

        if (usuario == null) {
            return Response.status(Status.NO_CONTENT)
                    .entity("Usuario não encontrado").build();
        }
        return Response.ok()
                .header("Authorization", jwtService.generateJwt(UsuarioResponseDTO.valueOf(usuario)))
                .build();

    }

    @POST
    @Path("/funcionario")
    @Produces(MediaType.TEXT_PLAIN)
    public Response loginFuncionario(@Valid AuthFuncionarioRequestDTO authDTO) {
        String hash = hashService.getHashSenha(authDTO.senha());

        Usuario usuario = usuarioService.findByUsernameAndSenha(authDTO.username(), hash);

        if (usuario == null) {
            return Response.status(Status.NO_CONTENT)
                    .entity("Usuario não encontrado").build();
        }

        authService.changeRole(usuario, authDTO.perfil());
        return Response.ok()
                .header("Authorization", jwtService.generateJwt(UsuarioResponseDTO.valueOf(usuario)))
                .build();

    }

}
package br.unitins.tp1.ironforge.resource.usuario;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.unitins.tp1.ironforge.dto.usuario.EmailPatchDTO;
import br.unitins.tp1.ironforge.dto.usuario.SenhaPatchDTO;
import br.unitins.tp1.ironforge.dto.usuario.UsuarioRequestDTO;
import br.unitins.tp1.ironforge.dto.usuario.UsuarioResponseDTO;
import br.unitins.tp1.ironforge.service.usuario.UsuarioService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/usuarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    public UsuarioService usuarioService;

    @Inject
    public JsonWebToken jsonWebToken;

    @POST
    public Response register(@Valid UsuarioRequestDTO dto) {
        return Response.status(Status.CREATED).entity(UsuarioResponseDTO.valueOf(usuarioService.create(dto))).build();
    }

    @PATCH
    @Path("/{id}/email")
    public Response updateEmail(@PathParam("id") Long id, @Valid EmailPatchDTO dto) {
        usuarioService.updateEmail(id, dto);
        return Response.noContent().build();
    }

    @PATCH
    @Path("/{id}/senha")

    public Response updateSenha(@PathParam("id") Long id, @Valid SenhaPatchDTO dto) {
        usuarioService.updateSenha(id, dto);
        return Response.noContent().build();
    }
}

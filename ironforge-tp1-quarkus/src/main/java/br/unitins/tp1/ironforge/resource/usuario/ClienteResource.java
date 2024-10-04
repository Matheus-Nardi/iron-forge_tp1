package br.unitins.tp1.ironforge.resource.usuario;

import br.unitins.tp1.ironforge.dto.usuario.UsuarioRequestDTO;
import br.unitins.tp1.ironforge.service.usuario.ClienteService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/clientes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteResource {

    @Inject
    public ClienteService clienteService;

    @POST
    public Response create(@Valid UsuarioRequestDTO usuario) {
        return Response.status(Status.CREATED).entity(clienteService.create(usuario)).build();
    }

}

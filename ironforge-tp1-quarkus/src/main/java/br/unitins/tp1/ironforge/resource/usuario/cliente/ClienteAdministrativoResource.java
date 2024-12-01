package br.unitins.tp1.ironforge.resource.usuario.cliente;

import java.util.List;

import br.unitins.tp1.ironforge.dto.pessoafisica.ClienteResponseDTO;
import br.unitins.tp1.ironforge.model.usuario.Cliente;
import br.unitins.tp1.ironforge.service.usuario.ClienteService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/clientes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteAdministrativoResource {

    @Inject
    public ClienteService clienteService;

    @GET
    @Path("/{id}")
    @RolesAllowed("Funcionario")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(ClienteResponseDTO.valueOf(clienteService.findById(id))).build();
    }

    @GET
    @Path("/search/{nome}")
    @RolesAllowed("Funcionario")
    public Response findByNome(@PathParam("nome") String nome) {
        List<Cliente> clientes = clienteService.findByNome(nome);
        return Response.ok(clientes.stream().map(ClienteResponseDTO::valueOf).toList()).build();
    }

    @GET
    @RolesAllowed("Funcionario")
    public Response findAll() {
        List<Cliente> clientes = clienteService.findAll();
        return Response.ok(clientes.stream().map(ClienteResponseDTO::valueOf).toList()).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("Funcionario")
    public Response delete(@PathParam("id") Long id) {
        clienteService.delete(id);
        return Response.noContent().build();
    }

}

package br.unitins.tp1.ironforge.resource.usuario;

import java.util.List;

import br.unitins.tp1.ironforge.dto.usuario.funcionario.FuncionarioRequestDTO;
import br.unitins.tp1.ironforge.dto.usuario.funcionario.FuncionarioResponseDTO;
import br.unitins.tp1.ironforge.model.usuario.Funcionario;
import br.unitins.tp1.ironforge.service.usuario.FuncionarioService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/funcionarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FuncionarioResource {

    @Inject
    public FuncionarioService funcionarioService;

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(FuncionarioResponseDTO.valueOf(funcionarioService.findById(id))).build();
    }

    @GET
    @Path("/search/{nome}")
    public Response findByNome(@PathParam("nome") String nome) {
        List<Funcionario> funcionarios = funcionarioService.findByNome(nome);
        return Response.ok(funcionarios.stream().map(FuncionarioResponseDTO::valueOf).toList()).build();
    }

    @GET
    public Response findAll() {
        List<Funcionario> funcionarios = funcionarioService.findAll();
        return Response.ok(funcionarios.stream().map(FuncionarioResponseDTO::valueOf).toList()).build();
    }

    @POST
    public Response create(@Valid FuncionarioRequestDTO dto) {
        return Response.status(Status.CREATED).entity(FuncionarioResponseDTO.valueOf(funcionarioService.create(dto)))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid FuncionarioRequestDTO dto) {
        funcionarioService.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        funcionarioService.delete(id);
        return Response.noContent().build();
    }

}

package br.unitins.tp1.ironforge.resource.usuario;

import br.unitins.tp1.ironforge.dto.pessoafisica.ClienteResponseDTO;
import br.unitins.tp1.ironforge.dto.pessoafisica.FuncionarioBasicoRequestDTO;
import br.unitins.tp1.ironforge.dto.pessoafisica.FuncionarioResponseDTO;
import br.unitins.tp1.ironforge.model.usuario.Cliente;
import br.unitins.tp1.ironforge.model.usuario.Funcionario;
import br.unitins.tp1.ironforge.service.jwt.JwtService;
import br.unitins.tp1.ironforge.service.usuario.AdministradorService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/administrador")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AdministradorResource {

    @Inject
    public AdministradorService administradorService;

    @Inject
    JwtService jwtService;

    @POST
    @Path("/clientes/{idCliente}/transformar-em-funcionario")
    @RolesAllowed("Administrador")
    public Response transformarClienteEmFuncionario(@PathParam("idCliente") Long idFuncionario,
            @Valid FuncionarioBasicoRequestDTO dto) {
        Funcionario funcionario = administradorService.transformarClienteEmFuncionario(idFuncionario, dto);
        return Response.status(Status.CREATED)
                .entity(FuncionarioResponseDTO.valueOf(funcionario))
                .build();
    }

    @POST
    @Path("/funcionarios/{idFuncionario}/transformar-em-cliente")
    @RolesAllowed("Administrador")
    public Response transformarFuncionarioEmCliente(@PathParam("idFuncionario") Long idFuncionario) {
        Cliente cliente = administradorService.transformarFuncionarioEmCliente(idFuncionario);
        return Response.status(Status.CREATED)
                .entity(ClienteResponseDTO.valueOf(cliente))
                .build();
    }

}
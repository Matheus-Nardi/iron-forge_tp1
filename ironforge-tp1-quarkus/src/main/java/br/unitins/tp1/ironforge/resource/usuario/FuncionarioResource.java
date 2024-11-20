package br.unitins.tp1.ironforge.resource.usuario;

import java.io.IOException;
import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import br.unitins.tp1.ironforge.dto.endereco.EnderecoRequestDTO;
import br.unitins.tp1.ironforge.dto.pessoafisica.FuncionarioRequestDTO;
import br.unitins.tp1.ironforge.dto.pessoafisica.FuncionarioResponseDTO;
import br.unitins.tp1.ironforge.dto.pessoafisica.FuncionarioUpdateRequestDTO;
import br.unitins.tp1.ironforge.dto.telefone.TelefoneRequestDTO;
import br.unitins.tp1.ironforge.form.ImageForm;
import br.unitins.tp1.ironforge.model.usuario.Funcionario;
import br.unitins.tp1.ironforge.service.FuncionarioFileServiceImpl;
import br.unitins.tp1.ironforge.service.usuario.FuncionarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;

@Path("/funcionarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FuncionarioResource {

    @Inject
    public FuncionarioService funcionarioService;

    @Inject
    public JsonWebToken jsonWebToken;

    @Inject
    public FuncionarioFileServiceImpl fileService;

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

    @PATCH
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid FuncionarioUpdateRequestDTO dto) {
        funcionarioService.update(id, dto);
        return Response.noContent().build();
    }

    @PATCH
    @Path("/{id}/telefones/{idTelefone}")
    public Response updateTelefones(@PathParam("id") Long id, @PathParam("idTelefone") Long idTelefone,
            @Valid TelefoneRequestDTO telefone) {
        funcionarioService.updateTelefone(id, idTelefone, telefone);
        return Response.noContent().build();
    }

    @PATCH
    @Path("/{id}/enderecos/{idEndereco}")
    public Response updateEnderecos(@PathParam("id") Long id, @PathParam("idEndereco") Long idEndereco,
            @Valid EnderecoRequestDTO endereco) {
        funcionarioService.updateEndereco(id, idEndereco, endereco);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        funcionarioService.delete(id);
        return Response.noContent().build();
    }

    @PATCH
    @Path("upload/imagens")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @RolesAllowed({ "Adm" })
    public Response uploadImage(@MultipartForm ImageForm form) {
        String username = jsonWebToken.getSubject();
        try {
            String nomeImagem = fileService.save(form.getNomeImagem(), form.getImagem());
            funcionarioService.updateNomeImagem(username, nomeImagem);
        } catch (IOException e) {
            Response.status(Status.INTERNAL_SERVER_ERROR).encoding("Não foi possível salvar a imagem").build();
        }

        return Response.noContent().build();
    }

    @GET
    @Path("/download/image/{nomeImagem}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @RolesAllowed({ "Adm" })
    public Response downloadImage(@PathParam("nomeImagem") String nomeImagem) {
        ResponseBuilder response = null;
        try {
            response = Response.ok(fileService.find(nomeImagem));
        } catch (IOException e) {
            Response.status(Status.INTERNAL_SERVER_ERROR).encoding("Não foi possível baixar a imagem").build();
        }
        response.header("Content-Disposition", "attachment; filename=" + nomeImagem);
        return response.build();
    }

}

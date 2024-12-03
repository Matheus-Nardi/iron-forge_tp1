package br.unitins.tp1.ironforge.resource.usuario.cliente;

import java.io.IOException;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import br.unitins.tp1.ironforge.dto.endereco.EnderecoRequestDTO;
import br.unitins.tp1.ironforge.dto.endereco.EnderecoResponseDTO;
import br.unitins.tp1.ironforge.dto.pessoafisica.ClienteRequestDTO;
import br.unitins.tp1.ironforge.dto.pessoafisica.ClienteResponseDTO;
import br.unitins.tp1.ironforge.dto.pessoafisica.ClienteUpdateRequestDTO;
import br.unitins.tp1.ironforge.dto.telefone.TelefoneRequestDTO;
import br.unitins.tp1.ironforge.dto.telefone.TelefoneResponseDTO;
import br.unitins.tp1.ironforge.dto.whey.WheyProteinResponseDTO;
import br.unitins.tp1.ironforge.form.ImageForm;
import br.unitins.tp1.ironforge.service.ClienteFileServiceImpl;
import br.unitins.tp1.ironforge.service.usuario.ClienteService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
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

@Path("/clientes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteResource {

    @Inject
    public ClienteService clienteService;

    @Inject
    public ClienteFileServiceImpl clienteFileService;

    @Inject
    public JsonWebToken jsonWebToken;

    @PATCH
    @Path("/adicao/{idProduto}")
    @RolesAllowed({ "Cliente" })
    public Response adicionarListaDesejo(@PathParam("idProduto") Long idProduto) {
        String username = jsonWebToken.getSubject();
        System.out.println(username);
        clienteService.adicionarListaDesejo(username, idProduto);
        return Response.noContent().build();
    }

    @PATCH
    @Path("/remocao/{idProduto}")
    @RolesAllowed({ "Cliente" })
    public Response removerListaDesejo(@PathParam("idProduto") Long idProduto) {
        String username = jsonWebToken.getSubject();
        clienteService.removerListaDesejo(username, idProduto);
        return Response.noContent().build();
    }

    @GET
    @Path("/desejos")
    @RolesAllowed({ "Cliente" })
    public Response getListaDesejos() {
        String username = jsonWebToken.getSubject();
        return Response.ok()
                .entity(clienteService.getListaDesejos(username).stream().map(WheyProteinResponseDTO::valueOf)).build();
    }

    @GET
    @Path("/meu-perfil")
    @RolesAllowed({ "Cliente" })
    public Response getPerfilCliente() {
        String username = jsonWebToken.getSubject();
        return Response.ok().entity(ClienteResponseDTO.valueOf(clienteService.findByUsuario(username))).build();
    }

    @POST
    @RolesAllowed({ "Usuario" })
    public Response create(@Valid ClienteRequestDTO dto) {
        String username = jsonWebToken.getSubject();
        return Response.status(Status.CREATED).entity(ClienteResponseDTO.valueOf(clienteService.create(username, dto)))
                .build();
    }

    @PATCH
    @RolesAllowed({ "Cliente" })
    public Response update(@Valid ClienteUpdateRequestDTO dto) {
        String username = jsonWebToken.getSubject();
        clienteService.update(username, dto);
        return Response.noContent().build();
    }

    @PATCH
    @Path("/telefones/{idTelefone}")
    @RolesAllowed({ "Cliente" })
    public Response updateTelefones(@PathParam("id") Long id, @PathParam("idTelefone") Long idTelefone,
            @Valid TelefoneRequestDTO telefone) {
        String username = jsonWebToken.getSubject();
        clienteService.updateTelefone(username, idTelefone, telefone);
        return Response.noContent().build();
    }

    @PATCH
    @Path("/enderecos/{idEndereco}")
    @RolesAllowed({ "Cliente" })
    public Response updateEnderecos(@PathParam("id") Long id, @PathParam("idEndereco") Long idEndereco,
            @Valid EnderecoRequestDTO endereco) {
        String username = jsonWebToken.getSubject();
        clienteService.updateEndereco(username, idEndereco, endereco);
        return Response.noContent().build();
    }

    @PATCH
    @Path("upload/imagens")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @RolesAllowed({ "Cliente" })
    public Response uploadImage(@MultipartForm ImageForm form) {
        String username = jsonWebToken.getSubject();
        try {
            String nomeImagem = clienteFileService.save(form.getNomeImagem(), form.getImagem());
            clienteService.updateNomeImagem(username, nomeImagem);
        } catch (IOException e) {
            Response.status(Status.INTERNAL_SERVER_ERROR).encoding("Não foi possível salvar a imagem").build();
        }

        return Response.noContent().build();
    }

    @GET
    @Path("/download/image/{nomeImagem}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @RolesAllowed({ "Cliente" })
    public Response downloadImage(@PathParam("nomeImagem") String nomeImagem) {
        ResponseBuilder response = null;
        try {
            response = Response.ok(clienteFileService.find(nomeImagem));
        } catch (IOException e) {
            Response.status(Status.INTERNAL_SERVER_ERROR).encoding("Não foi possível baixar a imagem").build();
        }
        response.header("Content-Disposition", "attachment; filename=" + nomeImagem);
        return response.build();
    }

    @POST
    @Path("adicao/endereco")
    @RolesAllowed({ "Cliente" })
    public Response addEndereco(@Valid EnderecoRequestDTO dto) {
        String username = jsonWebToken.getSubject();
        return Response.status(Status.CREATED)
                .entity(EnderecoResponseDTO.valueOf(clienteService.addEndereco(username, dto))).build();
    }

    @POST
    @Path("adicao/telefone")
    @RolesAllowed({ "Cliente" })
    public Response addTelefone(@Valid TelefoneRequestDTO dto) {
        String username = jsonWebToken.getSubject();
        return Response.status(Status.CREATED)
                .entity(TelefoneResponseDTO.valueOf(clienteService.addTelefone(username, dto))).build();
    }

}

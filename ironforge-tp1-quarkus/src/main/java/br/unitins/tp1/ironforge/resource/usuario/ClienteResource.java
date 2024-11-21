package br.unitins.tp1.ironforge.resource.usuario;

import java.io.IOException;
import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import br.unitins.tp1.ironforge.dto.endereco.EnderecoRequestDTO;
import br.unitins.tp1.ironforge.dto.pessoafisica.ClienteRequestDTO;
import br.unitins.tp1.ironforge.dto.pessoafisica.ClienteResponseDTO;
import br.unitins.tp1.ironforge.dto.pessoafisica.ClienteUpdateRequestDTO;
import br.unitins.tp1.ironforge.dto.telefone.TelefoneRequestDTO;
import br.unitins.tp1.ironforge.dto.whey.WheyProteinResponseDTO;
import br.unitins.tp1.ironforge.form.ImageForm;
import br.unitins.tp1.ironforge.model.usuario.Cliente;
import br.unitins.tp1.ironforge.service.ClienteFileServiceImpl;
import br.unitins.tp1.ironforge.service.usuario.ClienteService;
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

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(ClienteResponseDTO.valueOf(clienteService.findById(id))).build();
    }

    @GET
    @Path("/search/{nome}")
    public Response findByNome(@PathParam("nome") String nome) {
        List<Cliente> clientes = clienteService.findByNome(nome);
        return Response.ok(clientes.stream().map(ClienteResponseDTO::valueOf).toList()).build();
    }

    @PATCH
    @Path("/adicao/{idProduto}")
    @RolesAllowed({ "User" })
    public Response adicionarListaDesejo(@PathParam("idProduto") Long idProduto) {
        String username = jsonWebToken.getSubject();
        System.out.println(username);
        clienteService.adicionarListaDesejo(username, idProduto);
        return Response.noContent().build();
    }

    @PATCH
    @Path("/remocao/{idProduto}")
    @RolesAllowed({ "User" })
    public Response removerListaDesejo(@PathParam("idProduto") Long idProduto) {
        String username = jsonWebToken.getSubject();
        clienteService.removerListaDesejo(username, idProduto);
        return Response.noContent().build();
    }

    @GET
    @Path("/desejos")
    @RolesAllowed({ "User" })
    public Response getListaDesejos() {
        String username = jsonWebToken.getSubject();
        return Response.ok()
                .entity(clienteService.getListaDesejos(username).stream().map(WheyProteinResponseDTO::valueOf)).build();
    }

    @GET
    public Response findAll() {
        List<Cliente> clientes = clienteService.findAll();
        return Response.ok(clientes.stream().map(ClienteResponseDTO::valueOf).toList()).build();
    }

    @POST
    public Response create(@Valid ClienteRequestDTO dto) {
        return Response.status(Status.CREATED).entity(ClienteResponseDTO.valueOf(clienteService.create(dto))).build();
    }

    @PATCH
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid ClienteUpdateRequestDTO dto) {
        clienteService.update(id, dto);
        return Response.noContent().build();
    }

    @PATCH
    @Path("/{id}/telefones/{idTelefone}")
    public Response updateTelefones(@PathParam("id") Long id, @PathParam("idTelefone") Long idTelefone,
            @Valid TelefoneRequestDTO telefone) {
        clienteService.updateTelefone(id, idTelefone, telefone);
        return Response.noContent().build();
    }

    @PATCH
    @Path("/{id}/enderecos/{idEndereco}")
    public Response updateEnderecos(@PathParam("id") Long id, @PathParam("idEndereco") Long idEndereco,
            @Valid EnderecoRequestDTO endereco) {
        clienteService.updateEndereco(id, idEndereco, endereco);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        clienteService.delete(id);
        return Response.noContent().build();
    }

    @PATCH
    @Path("upload/imagens")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @RolesAllowed({ "User" })
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
    @RolesAllowed({ "User" })
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

}

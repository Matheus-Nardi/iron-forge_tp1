package br.unitins.tp1.ironforge.resource;

import java.io.IOException;
import java.util.List;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import br.unitins.tp1.ironforge.dto.whey.WheyProteinRequestDTO;
import br.unitins.tp1.ironforge.dto.whey.WheyProteinResponseDTO;
import br.unitins.tp1.ironforge.form.ImageForm;
import br.unitins.tp1.ironforge.model.whey.TipoWhey;
import br.unitins.tp1.ironforge.model.whey.WheyProtein;
import br.unitins.tp1.ironforge.service.WheyProteinFileServiceImpl;
import br.unitins.tp1.ironforge.service.whey.WheyProteinService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;

@Path("/wheys")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WheyProteinResource {

    @Inject
    public WheyProteinService wheyService;

    @Inject
    public WheyProteinFileServiceImpl fileService;

    @GET
    public Response getAll() {
        List<WheyProtein> wheys = wheyService.findAll();
        return Response.ok(wheys.stream().map(WheyProteinResponseDTO::valueOf)).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(WheyProteinResponseDTO.valueOf(wheyService.findById(id))).build();
    }

    @GET
    @Path("/search/nome")
    public Response findByNome(@QueryParam("nome") String nome) {
        List<WheyProtein> wheys = wheyService.findByNome(nome);
        return Response.ok(wheys.stream().map(WheyProteinResponseDTO::valueOf).toList()).build();
    }

    @GET
    @Path("/search/sabor")
    public Response findBySabor(@QueryParam("sabor") String sabor) {
        List<WheyProtein> wheys = wheyService.findBySabor(sabor);
        return Response.ok(wheys.stream().map(WheyProteinResponseDTO::valueOf).toList()).build();
    }

    @GET
    @Path("/search/tipo")
    public Response findByTipoWhey(@QueryParam("tipo") TipoWhey tipo) {
        List<WheyProtein> wheys = wheyService.findByTipoWhey(tipo);
        return Response.ok(wheys.stream().map(WheyProteinResponseDTO::valueOf).toList()).build();
    }

    @GET
    @Path("/search/preco")
    public Response findByPreco(@QueryParam("preco") Double preco) {
        List<WheyProtein> wheys = wheyService.findByPreco(preco);
        return Response.ok(wheys.stream().map(WheyProteinResponseDTO::valueOf).toList()).build();
    }

    @GET
    @Path("/search/preco/min/{precoMin}/max/{precoMax}")
    public Response findByPrecoMinAndMax(@PathParam("precoMin") Double precoMin,
            @PathParam("precoMax") Double precoMax) {
        List<WheyProtein> wheys = wheyService.findByPrecoMinAndMax(precoMin, precoMax);
        return Response.ok(wheys.stream().map(WheyProteinResponseDTO::valueOf).toList()).build();
    }

    @POST
    public Response create(WheyProteinRequestDTO wheyProteinDTO) {
        WheyProtein whey = wheyService.create(wheyProteinDTO);
        return Response.status(Status.CREATED).entity(WheyProteinResponseDTO.valueOf(whey)).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, WheyProteinRequestDTO wheyToUpdate) {
        wheyService.update(id, wheyToUpdate);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        wheyService.delete(id);
        return Response.noContent().build();
    }

    @PATCH
    @Path("/{id}/upload/imagens")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @RolesAllowed({ "Adm" })
    public Response uploadImage(@PathParam("id") Long id, @MultipartForm ImageForm form) {
        try {
            String nomeImagem = fileService.save(form.getNomeImagem(), form.getImagem());
            wheyService.updateNomeImagem(id, nomeImagem);
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

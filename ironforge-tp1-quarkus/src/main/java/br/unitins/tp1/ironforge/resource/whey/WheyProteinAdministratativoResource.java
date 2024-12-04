package br.unitins.tp1.ironforge.resource.whey;

import java.io.IOException;
import java.util.List;

import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import br.unitins.tp1.ironforge.dto.whey.WheyProteinRequestDTO;
import br.unitins.tp1.ironforge.dto.whey.WheyProteinResponseDTO;
import br.unitins.tp1.ironforge.form.ImageForm;
import br.unitins.tp1.ironforge.model.whey.WheyProtein;
import br.unitins.tp1.ironforge.service.file.WheyProteinFileServiceImpl;
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

@Path("/administrativo/wheys")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WheyProteinAdministratativoResource {

    private static final Logger LOG = Logger.getLogger(WheyProteinAdministratativoResource.class);

    @Inject
    public WheyProteinService wheyService;

    @Inject
    public WheyProteinFileServiceImpl fileService;

    @GET
    @RolesAllowed({"Funcionario" })
    public Response getAll() {
        LOG.infof("Buscando todos os wheys");
        List<WheyProtein> wheys = wheyService.findAll();
        return Response.ok(wheys.stream().map(WheyProteinResponseDTO::valueOf)).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "Administrador", "Funcionario" })
    public Response findById(@PathParam("id") Long id) {
        LOG.infof("Buscando whey com ID: %d", id);
        return Response.ok(WheyProteinResponseDTO.valueOf(wheyService.findById(id))).build();
    }

    @GET
    @Path("/search/nome")
    @RolesAllowed({ "Administrador", "Funcionario" })
    public Response findByNome(@QueryParam("nome") String nome) {
        LOG.infof("Buscando wheys com nome: %s", nome);
        List<WheyProtein> wheys = wheyService.findByNome(nome);
        return Response.ok(wheys.stream().map(WheyProteinResponseDTO::valueOf).toList()).build();
    }

    @POST
    @RolesAllowed({ "Administrador", "Funcionario" })
    public Response create(WheyProteinRequestDTO wheyProteinDTO) {
        LOG.infof("Criando novo whey: %s", wheyProteinDTO);
        WheyProtein whey = wheyService.create(wheyProteinDTO);
        return Response.status(Status.CREATED).entity(WheyProteinResponseDTO.valueOf(whey)).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({ "Administrador", "Funcionario" })
    public Response update(@PathParam("id") Long id, WheyProteinRequestDTO wheyToUpdate) {
        LOG.infof("Atualizando whey com ID: %d", id);
        wheyService.update(id, wheyToUpdate);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({ "Administrador", "Funcionario" })
    public Response delete(@PathParam("id") Long id) {
        LOG.infof("Deletando whey com ID: %d", id);
        wheyService.delete(id);
        return Response.noContent().build();
    }

    @PATCH
    @Path("/{id}/upload/imagens")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @RolesAllowed({ "Administrador", "Funcionario" })
    public Response uploadImage(@PathParam("id") Long id, @MultipartForm ImageForm form) {
        LOG.infof("Fazendo upload de imagem para whey com ID: %d", id);
        try {
            String nomeImagem = fileService.save(form.getNomeImagem(), form.getImagem());
            wheyService.updateNomeImagem(id, nomeImagem);
        } catch (IOException e) {
            LOG.errorf("Erro ao salvar a imagem para whey com ID: %d. Detalhes: %s", id, e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).encoding("Não foi possível salvar a imagem").build();
        }
        return Response.noContent().build();
    }

    @GET
    @Path("/download/image/{nomeImagem}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @RolesAllowed({ "Administrador", "Funcionario" })
    public Response downloadImage(@PathParam("nomeImagem") String nomeImagem) {
        LOG.infof("Baixando imagem: %s", nomeImagem);
        ResponseBuilder response;
        try {
            response = Response.ok(fileService.find(nomeImagem));
        } catch (IOException e) {
            LOG.errorf("Erro ao baixar a imagem: %s. Detalhes: %s", nomeImagem, e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).encoding("Não foi possível baixar a imagem").build();
        }
        response.header("Content-Disposition", "attachment; filename=" + nomeImagem);
        return response.build();
    }
}

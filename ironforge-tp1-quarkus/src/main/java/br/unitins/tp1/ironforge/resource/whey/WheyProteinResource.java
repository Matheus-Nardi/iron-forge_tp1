package br.unitins.tp1.ironforge.resource.whey;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.tp1.ironforge.dto.whey.WheyProteinResponseDTO;
import br.unitins.tp1.ironforge.model.whey.TipoWhey;
import br.unitins.tp1.ironforge.model.whey.WheyProtein;
import br.unitins.tp1.ironforge.service.file.WheyProteinFileServiceImpl;
import br.unitins.tp1.ironforge.service.whey.WheyProteinService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/wheys")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WheyProteinResource {

    private static final Logger LOG = Logger.getLogger(WheyProteinResource.class);

    @Inject
    public WheyProteinService wheyService;

    @Inject
    public WheyProteinFileServiceImpl fileService;

    @GET
    public Response getAll() {
        LOG.infof("Buscando todos os wheys");
        List<WheyProtein> wheys = wheyService.findAll();
        return Response.ok(wheys.stream().map(WheyProteinResponseDTO::valueOf)).build();
    }

    @GET
    @Path("/search/nome")
    public Response findByNome(@QueryParam("nome") String nome) {
        LOG.infof("Buscando wheys com nome: %s", nome);
        List<WheyProtein> wheys = wheyService.findByNome(nome);
        return Response.ok(wheys.stream().map(WheyProteinResponseDTO::valueOf).toList()).build();
    }

    @GET
    @Path("/search/sabor")
    public Response findBySabor(@QueryParam("sabor") String sabor) {
        LOG.infof("Buscando wheys com sabor: %s", sabor);
        List<WheyProtein> wheys = wheyService.findBySabor(sabor);
        return Response.ok(wheys.stream().map(WheyProteinResponseDTO::valueOf).toList()).build();
    }

    @GET
    @Path("/search/tipo")
    public Response findByTipoWhey(@QueryParam("tipo") TipoWhey tipo) {
        LOG.infof("Buscando wheys com tipo: %s", tipo.getLabel());
        List<WheyProtein> wheys = wheyService.findByTipoWhey(tipo);
        return Response.ok(wheys.stream().map(WheyProteinResponseDTO::valueOf).toList()).build();
    }

    @GET
    @Path("/search/preco")
    public Response findByPreco(@QueryParam("preco") Double preco) {
        LOG.infof("Buscando wheys com preço até: %f", preco);
        List<WheyProtein> wheys = wheyService.findByPreco(preco);
        return Response.ok(wheys.stream().map(WheyProteinResponseDTO::valueOf).toList()).build();
    }

    @GET
    @Path("/search/preco/min/{precoMin}/max/{precoMax}")
    public Response findByPrecoMinAndMax(@PathParam("precoMin") Double precoMin,
            @PathParam("precoMax") Double precoMax) {
        LOG.infof("Buscando wheys com preço entre %f e %f", precoMin, precoMax);
        List<WheyProtein> wheys = wheyService.findByPrecoMinAndMax(precoMin, precoMax);
        return Response.ok(wheys.stream().map(WheyProteinResponseDTO::valueOf).toList()).build();
    }

    @GET
    @Path("/search/nota")
    public Response findMostRated() {
        LOG.infof("Buscando wheys com base na nota");
        return Response.ok(wheyService.findByMostRated().stream().map(WheyProteinResponseDTO::valueOf).toList())
                .build();
    }

}

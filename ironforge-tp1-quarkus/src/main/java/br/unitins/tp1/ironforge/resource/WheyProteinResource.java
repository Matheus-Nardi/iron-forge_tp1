package br.unitins.tp1.ironforge.resource;

import java.util.List;

import br.unitins.tp1.ironforge.model.WheyProtein;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/wheys")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WheyProteinResource {

    @GET
    public List<WheyProtein> getAll(){
        return WheyProtein.listAll();
    }
}

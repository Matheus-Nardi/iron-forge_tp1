package br.unitins.tp1.ironforge.validation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class EntidadeNotFoundExceptionMapper implements ExceptionMapper<EntidadeNotFoundException> {

    @Override
    public Response toResponse(EntidadeNotFoundException exception) {
        ValidationError error = new ValidationError("404", "Entidade não encontrada");
        error.addFieldError(exception.getFieldName(), exception.getMessage());

        return Response.status(Status.NOT_FOUND).entity(error).build();
    }

}

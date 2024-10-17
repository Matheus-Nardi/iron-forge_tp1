package br.unitins.tp1.ironforge.infra.mapper;

import java.time.LocalDateTime;

import br.unitins.tp1.ironforge.infra.exception.NotFoundException;
import br.unitins.tp1.ironforge.util.ApiError;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException exception) {
        ApiError error = new ApiError();
        error.setCode(Response.Status.NOT_FOUND.getStatusCode());
        error.setMessage(exception.getMessage());
        error.setPath(Response.Status.NOT_FOUND.getReasonPhrase());
        error.setTimestamp(LocalDateTime.now());

        return Response.status(Status.NOT_FOUND).entity(error).build();
    }

}

package br.unitins.tp1.ironforge.validation;

import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

    private static final Logger LOG = Logger.getLogger(ValidationExceptionMapper.class);

    @Override
    public Response toResponse(ValidationException exception) {
        LOG.errorf("Validation Error %s ", exception.getMessage());
        ValidationError error = new ValidationError("400", "Erro de validação");
        error.addFieldError(exception.getFieldName(), exception.getMessage());

        return Response.status(Status.BAD_REQUEST).entity(error).build();
    }

}

package br.unitins.tp1.ironforge.infra.mapper;

import java.time.LocalDateTime;

import br.unitins.tp1.ironforge.util.ValidationError;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class BeanValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        ValidationError validationError = new ValidationError();
        validationError.setCode(Response.Status.BAD_REQUEST.getStatusCode());
        validationError.setPath(Response.Status.BAD_REQUEST.getReasonPhrase());
        validationError.setMessage("Erro de validação");
        validationError.setTimestamp(LocalDateTime.now());

        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            String fullFieldName = violation.getPropertyPath().toString();
            String parts[] = fullFieldName.split("\\.");
            String fieldName = parts[parts.length - 1];
            String message = violation.getMessage();
            validationError.addFieldError(fieldName, message);
        }

        return Response.status(Response.Status.BAD_REQUEST).entity(validationError).build();

    }

}

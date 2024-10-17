package br.unitins.tp1.ironforge.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends ApiError {

    record FieldError(String fieldName, String message) {
    };

    private List<FieldError> errors;

    public ValidationError() {
        super();
    }

    public ValidationError(int code, String message, String path, LocalDateTime timestamp) {
        super(code, message, path, timestamp);
        this.errors = new ArrayList<>();
    }

    public void addFieldError(String fieldName, String message) {
        if (errors == null)
            errors = new ArrayList<FieldError>();
        errors.add(new FieldError(fieldName, message));
    }

    public List<FieldError> getErrors() {
        return errors.stream().toList();
    }
}

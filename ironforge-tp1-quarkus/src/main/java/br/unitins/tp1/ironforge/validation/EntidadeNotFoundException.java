package br.unitins.tp1.ironforge.validation;

public class EntidadeNotFoundException extends RuntimeException {

    private String fieldName;

    public EntidadeNotFoundException(String fieldName, String message) {
        super(message);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}

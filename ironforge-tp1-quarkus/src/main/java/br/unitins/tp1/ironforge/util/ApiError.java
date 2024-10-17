package br.unitins.tp1.ironforge.util;

import java.time.LocalDateTime;

public class ApiError {

    private int code;
    private String message;
    private String path;
    private LocalDateTime timestamp;

    public ApiError() {
        super();
    }

    

    public ApiError(int code, String message, String path, LocalDateTime timestamp) {
        this.code = code;
        this.message = message;
        this.path = path;
        this.timestamp = timestamp;
    }



    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    

}

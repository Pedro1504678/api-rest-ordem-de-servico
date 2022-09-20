package com.bastos.os.services.exceptions;

public class DataIntegratyViolationException extends RuntimeException {


    public DataIntegratyViolationException(String message) {
        super(message);
    }

    public DataIntegratyViolationException(String message, Throwable cause) {
        super(message, cause);
    }
}

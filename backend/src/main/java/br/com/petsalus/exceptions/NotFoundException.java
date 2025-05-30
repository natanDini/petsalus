package br.com.petsalus.exceptions;

import br.com.petsalus.enums.SeverityStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends CustomException {

    private static final String NOT_FOUND_EXCEPTION_DEFAULT_MESSAGE = "Recurso informado não encontrado.";

    public NotFoundException() {
        super(NOT_FOUND_EXCEPTION_DEFAULT_MESSAGE, SeverityStatus.ERROR, HttpStatus.NOT_FOUND);
    }

    public NotFoundException(String message) {
        super(message, SeverityStatus.ERROR, HttpStatus.NOT_FOUND);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause, SeverityStatus.ERROR, HttpStatus.NOT_FOUND);
    }

    public NotFoundException(Throwable cause) {
        super(cause, SeverityStatus.ERROR, HttpStatus.NOT_FOUND);
    }

    public NotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, SeverityStatus.ERROR, HttpStatus.NOT_FOUND);
    }
}
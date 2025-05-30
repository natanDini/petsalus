package br.com.petsalus.exceptions;

import br.com.petsalus.enums.SeverityStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends CustomException{

    private static final String CONFLICT_EXCEPTION_DEFAULT_MESSAGE = "Os dados inseridos conflitam com os já cadastrados.";

    public ConflictException() {
        super(CONFLICT_EXCEPTION_DEFAULT_MESSAGE, SeverityStatus.ERROR, HttpStatus.CONFLICT);
    }

    public ConflictException(String message) {
        super(message, SeverityStatus.ERROR, HttpStatus.CONFLICT);
    }

    public ConflictException(String message, Throwable cause) {
        super(message, cause, SeverityStatus.ERROR, HttpStatus.CONFLICT);
    }

    public ConflictException(Throwable cause) {
        super(cause, SeverityStatus.ERROR, HttpStatus.CONFLICT);
    }

    public ConflictException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, SeverityStatus.ERROR, HttpStatus.CONFLICT);
    }
}
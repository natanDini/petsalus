package br.com.petsalus.exceptions;

import br.com.petsalus.enums.SeverityStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends CustomException {

    private static final String UNAUTHORIZED_EXCEPTION_DEFAULT_MESSAGE = "Acesso negado.";

    public UnauthorizedException() {
        super(UNAUTHORIZED_EXCEPTION_DEFAULT_MESSAGE, SeverityStatus.ERROR, HttpStatus.UNAUTHORIZED);
    }

    public UnauthorizedException(String message) {
        super(message, SeverityStatus.ERROR, HttpStatus.UNAUTHORIZED);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause, SeverityStatus.ERROR, HttpStatus.UNAUTHORIZED);
    }

    public UnauthorizedException(Throwable cause) {
        super(cause, SeverityStatus.ERROR, HttpStatus.UNAUTHORIZED);
    }

    public UnauthorizedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, SeverityStatus.ERROR, HttpStatus.UNAUTHORIZED);
    }
}
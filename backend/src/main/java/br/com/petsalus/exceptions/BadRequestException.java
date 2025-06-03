package br.com.petsalus.exceptions;

import br.com.petsalus.enums.SeverityStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends CustomException {

    private static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;
    private static final SeverityStatus SEVERITY = SeverityStatus.ERROR;
    private static final String DEFAULT_MESSAGE = "Requisição ruim.";

    public BadRequestException() {
        super(DEFAULT_MESSAGE, SEVERITY, STATUS);
    }

    public BadRequestException(String message) {
        super(message, SEVERITY, STATUS);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause, SEVERITY, STATUS);
    }

    public BadRequestException(Throwable cause) {
        super(cause, SEVERITY, STATUS);
    }

    public BadRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, SEVERITY, STATUS);
    }
}

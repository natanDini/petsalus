package br.com.petsalus.exceptions;

import br.com.petsalus.enums.SeverityStatus;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException{

    private final HttpStatus httpStatus;
    private final SeverityStatus severity;

    public CustomException(SeverityStatus severity, HttpStatus httpStatus) {
        this.severity = severity;
        this.httpStatus = httpStatus;
    }

    public CustomException(String message, SeverityStatus severity, HttpStatus httpStatus) {
        super(message);
        this.severity = severity;
        this.httpStatus = httpStatus;
    }

    public CustomException(String message, Throwable cause, SeverityStatus severity, HttpStatus httpStatus) {
        super(message, cause);
        this.severity = severity;
        this.httpStatus = httpStatus;
    }

    public CustomException(Throwable cause, SeverityStatus severity, HttpStatus httpStatus) {
        super(cause);
        this.severity = severity;
        this.httpStatus = httpStatus;
    }

    public CustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, SeverityStatus severity, HttpStatus httpStatus) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.severity = severity;
        this.httpStatus = httpStatus;
    }
}
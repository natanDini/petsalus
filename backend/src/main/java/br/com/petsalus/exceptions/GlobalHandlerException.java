package br.com.petsalus.exceptions;

import br.com.petsalus.dtos.response.RetornoException;
import br.com.petsalus.services.RetornoExceptionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalHandlerException {

    private final RetornoExceptionService retornoExceptionService;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RetornoException> handleException(Exception ex, HttpServletRequest request) {
        String path = request.getRequestURI();
        log.error("Exceção capturada: {}", ex.getMessage(), ex);
        return retornoExceptionService.buildInternalServerErrorResponse(path);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<RetornoException> handleRuntimeException(RuntimeException ex, HttpServletRequest request) {
        String path = request.getRequestURI();
        log.error("Exceção capturada: {}", ex.getMessage(), ex);
        return retornoExceptionService.buildInternalServerErrorResponse(path);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<RetornoException> handleCustomException(CustomException ex, HttpServletRequest request) {
        String path = request.getRequestURI();
        log.error("Exceção capturada: {}", ex.getMessage(), ex);
        return retornoExceptionService.buildResponse(ex.getMessage(), ex.getSeverity(), ex.getHttpStatus(), path);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RetornoException> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                         HttpServletRequest request) {

        String message = ex.getBindingResult().getFieldErrors().stream().findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).orElse("Dados inválidos.");

        String path = request.getRequestURI();

        return  retornoExceptionService.buildBadRequestResponse(message, path);
    }
}
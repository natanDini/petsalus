package br.com.petsalus.utils;

import br.com.petsalus.exceptions.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmptyUtils {

    private final CustomExceptionUtils customExceptionUtils;

    public void validaListaVazia(List<?> lista, String mensagem) throws CustomException {
        if (lista.isEmpty()) {
            log.info(" >>> Lista vazia: {}", mensagem);
            throw customExceptionUtils.successAndNoContent(mensagem);
        }
    }
}
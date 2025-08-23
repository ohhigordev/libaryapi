package io.github.ohhigordev.libaryapi.controller.common;

import io.github.ohhigordev.libaryapi.controller.dto.ErroCampo;
import io.github.ohhigordev.libaryapi.controller.dto.ErroResposta;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // Objetivo da classe é capturar exceptions e retornar uma resposta de erro

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErroCampo> listaErros= fieldErrors.stream()
                .map(fe -> new ErroCampo(fe.getField(),fe.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação",
                listaErros);
    }

}

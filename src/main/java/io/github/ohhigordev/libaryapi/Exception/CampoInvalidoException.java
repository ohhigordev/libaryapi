package io.github.ohhigordev.libaryapi.Exception;

import lombok.Getter;

public class CampoInvalidoException extends RuntimeException {

    @Getter
    private String campo;

    public CampoInvalidoException(String campo, String mensage){
        super(mensage);
        this.campo = campo;
    }

}

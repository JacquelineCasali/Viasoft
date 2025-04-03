package com.gestao.infra.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FornecedorNaoEncontradoException extends  RuntimeException {
    public FornecedorNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}

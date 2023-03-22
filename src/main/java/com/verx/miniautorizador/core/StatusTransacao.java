package com.verx.miniautorizador.core;

import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum StatusTransacao {

    OK(HttpStatus.CREATED),
    SALDO_INSUFICIENTE(HttpStatus.UNPROCESSABLE_ENTITY),
    SENHA_INVALIDA(HttpStatus.UNPROCESSABLE_ENTITY),
    CARTAO_INEXISTENTE(HttpStatus.UNPROCESSABLE_ENTITY);
    private HttpStatus httpStatus;
    StatusTransacao(HttpStatus status) {
        this.httpStatus = status;
    }
}

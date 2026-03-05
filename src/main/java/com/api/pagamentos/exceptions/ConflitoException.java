package com.api.pagamentos.exceptions;

/**
 * Exceção para indicar conflito de dados já existentes.
 */
public class ConflitoException extends RuntimeException {

    public ConflitoException(String mensagem) {
        super(mensagem);
    }
}
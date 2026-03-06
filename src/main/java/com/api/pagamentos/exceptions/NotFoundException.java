package com.api.pagamentos.exceptions;

/**
 * Exceção para indicar que um recurso não foi encontrado.
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
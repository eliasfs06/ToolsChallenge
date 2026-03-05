package com.api.pagamentos.exceptions;

/**
 * Exceção para regras/validações de negócio.
 */
public class NegocioException extends RuntimeException {

    public NegocioException(String mensagem) {
        super(mensagem);
    }
}
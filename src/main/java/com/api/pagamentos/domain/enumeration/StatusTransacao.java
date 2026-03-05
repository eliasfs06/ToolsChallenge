package com.api.pagamentos.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Representa o status de uma transação.
 */
public enum StatusTransacao {

    AUTORIZADO("AUTORIZADO"),
    NEGADO("NEGADO"),
    CANCELADO("CANCELADO");

    private final String valorJson;

    StatusTransacao(String valorJson) {
        this.valorJson = valorJson;
    }

    /**
     * Retorna o valor do enum no formato esperado no JSON.
     *
     * @return valor textual usado no JSON.
     */
    @JsonValue
    public String getValorJson() {
        return valorJson;
    }

    /**
     * Converte o texto recebido no JSON para o enum correspondente.
     *
     * @param valor texto do status.
     * @return enum correspondente.
     */
    @JsonCreator
    public static StatusTransacao fromJson(String valor) {
        if (valor == null) {
            return null;
        }
        for (StatusTransacao status : values()) {
            if (status.valorJson.equalsIgnoreCase(valor.trim())) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status de transação inválido: " + valor);
    }
}
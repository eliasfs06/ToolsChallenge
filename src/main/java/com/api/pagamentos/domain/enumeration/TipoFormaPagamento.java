package com.api.pagamentos.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Representa o tipo da forma de pagamento.
 */
public enum TipoFormaPagamento {

    AVISTA("AVISTA"),
    PARCELADO_LOJA("PARCELADO LOJA"),
    PARCELADO_EMISSOR("PARCELADO EMISSOR");

    private final String valorJson;

    TipoFormaPagamento(String valorJson) {
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
     * @param valor texto do tipo.
     * @return enum correspondente.
     */
    @JsonCreator
    public static TipoFormaPagamento fromJson(String valor) {
        if (valor == null) {
            return null;
        }
        for (TipoFormaPagamento tipo : values()) {
            if (tipo.valorJson.equalsIgnoreCase(valor.trim())) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de forma de pagamento inválido: " + valor);
    }
}
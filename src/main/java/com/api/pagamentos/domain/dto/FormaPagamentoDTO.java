package com.api.pagamentos.domain.dto;

import com.api.pagamentos.domain.enumeration.TipoFormaPagamento;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * DTO que representa a forma de pagamento no contrato HTTP.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FormaPagamentoDTO {

    private TipoFormaPagamento tipo;
    private Integer parcelas;

    public TipoFormaPagamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoFormaPagamento tipo) {
        this.tipo = tipo;
    }

    public Integer getParcelas() {
        return parcelas;
    }

    public void setParcelas(Integer parcelas) {
        this.parcelas = parcelas;
    }
}

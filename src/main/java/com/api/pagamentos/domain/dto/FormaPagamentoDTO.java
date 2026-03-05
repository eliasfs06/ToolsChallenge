package com.api.pagamentos.domain.dto;

import com.api.pagamentos.domain.enumeration.TipoFormaPagamento;
import jakarta.validation.constraints.NotNull;

/**
 * DTO que representa a forma de pagamento no contrato HTTP.
 */
public class FormaPagamentoDTO {

    @NotNull(message = "O campo tipo é obrigatório.")
    private TipoFormaPagamento tipo;

    @NotNull(message = "O campo parcelas é obrigatório.")
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
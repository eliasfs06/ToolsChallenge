package com.api.pagamentos.domain.model;

import com.api.pagamentos.domain.enumeration.TipoFormaPagamento;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

/**
 * Representa a forma de pagamento de uma transação.
 */
@Embeddable
public class FormaPagamento {

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pagamento", nullable = false, length = 30)
    private TipoFormaPagamento tipo;

    @Column(name = "parcelas", nullable = false)
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
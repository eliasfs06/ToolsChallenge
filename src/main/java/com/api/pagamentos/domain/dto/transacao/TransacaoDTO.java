package com.api.pagamentos.domain.dto.transacao;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO que representa a transação no contrato HTTP.
 */
public class TransacaoDTO {

    @NotBlank(message = "O campo cartão é obrigatório.")
    private String cartao;

    @NotBlank(message = "O campo id é obrigatório.")
    private String id;

    @NotNull(message = "O campo descrição é obrigatório.")
    @Valid
    private DescricaoDTO descricao;

    @NotNull(message = "O campo formaPagamento é obrigatório.")
    @Valid
    private FormaPagamentoDTO formaPagamento;


    public String getCartao() {
        return cartao;
    }

    public void setCartao(String cartao) {
        this.cartao = cartao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DescricaoDTO getDescricao() {
        return descricao;
    }

    public void setDescricao(DescricaoDTO descricao) {
        this.descricao = descricao;
    }

    public FormaPagamentoDTO getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamentoDTO formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
}
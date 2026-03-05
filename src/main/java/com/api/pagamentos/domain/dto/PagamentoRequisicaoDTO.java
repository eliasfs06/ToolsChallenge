package com.api.pagamentos.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * DTO de requisição de pagamento.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PagamentoRequisicaoDTO {

    @NotNull(message = "O campo transação é obrigatório.")
    @Valid
    private TransacaoDTO transacao;

    @NotNull(message = "O campo formaPagamento é obrigatório.")
    @Valid
    private FormaPagamentoDTO formaPagamento;

    public TransacaoDTO getTransacao() {
        return transacao;
    }

    public void setTransacao(TransacaoDTO transacao) {
        this.transacao = transacao;
    }

    public FormaPagamentoDTO getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamentoDTO formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
}
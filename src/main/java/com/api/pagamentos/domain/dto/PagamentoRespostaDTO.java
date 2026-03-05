package com.api.pagamentos.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * DTO de resposta para pagamento, consulta e estorno.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PagamentoRespostaDTO {

    private TransacaoDTO transacao;
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

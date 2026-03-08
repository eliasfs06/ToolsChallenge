package com.api.pagamentos.domain.dto.transacao;

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

    public TransacaoDTO getTransacao() {
        return transacao;
    }

    public void setTransacao(TransacaoDTO transacao) {
        this.transacao = transacao;
    }
}
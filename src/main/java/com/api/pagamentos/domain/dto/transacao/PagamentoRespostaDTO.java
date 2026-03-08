package com.api.pagamentos.domain.dto.transacao;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * DTO de resposta para pagamento, consulta e estorno.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PagamentoRespostaDTO {

    private TransacaoDTO transacao;

    public TransacaoDTO getTransacao() {
        return transacao;
    }

    public void setTransacao(TransacaoDTO transacao) {
        this.transacao = transacao;
    }

}

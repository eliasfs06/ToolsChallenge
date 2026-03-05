package com.api.pagamentos.domain.dto;

import com.api.pagamentos.domain.enumeration.StatusTransacao;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * DTO que representa a transação no contrato HTTP (entrada/saída).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransacaoDTO {

    private String cartao;
    private String id;
    private DescricaoDTO descricao;

    private String nsu;
    private String codigoAutorizacao;
    private StatusTransacao status;

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

    public String getNsu() {
        return nsu;
    }

    public void setNsu(String nsu) {
        this.nsu = nsu;
    }

    public String getCodigoAutorizacao() {
        return codigoAutorizacao;
    }

    public void setCodigoAutorizacao(String codigoAutorizacao) {
        this.codigoAutorizacao = codigoAutorizacao;
    }

    public StatusTransacao getStatus() {
        return status;
    }

    public void setStatus(StatusTransacao status) {
        this.status = status;
    }
}
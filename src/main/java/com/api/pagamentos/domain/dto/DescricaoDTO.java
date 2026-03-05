package com.api.pagamentos.domain.dto;

import com.api.pagamentos.domain.enumeration.StatusTransacao;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO com os dados descritivos da transação.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DescricaoDTO {

    @NotNull(message = "O campo valor é obrigatório.")
    @DecimalMin(value = "0.01", message = "O campo valor deve ser maior que zero.")
    private BigDecimal valor;

    @NotNull(message = "O campo dataHora é obrigatório.")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataHora;

    @NotBlank(message = "O campo estabelecimento é obrigatório.")
    private String estabelecimento;

    private String nsu;
    private String codigoAutorizacao;
    private StatusTransacao status;

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(String estabelecimento) {
        this.estabelecimento = estabelecimento;
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
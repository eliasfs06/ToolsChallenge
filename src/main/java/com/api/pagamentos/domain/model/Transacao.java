package com.api.pagamentos.domain.model;

import com.api.pagamentos.domain.enumeration.StatusTransacao;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Representa uma transação de pagamento, contendo dados do cartão,
 * identificação, descrição, forma de pagamento e campos de retorno.
 */
@Entity
@Table(
    name = "transacao",
    indexes = {
            @Index(name = "idx_transacao_id_negocio", columnList = "id_negocio", unique = true)
    }
)
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Identificador de negócio da transação (id informado/consultado externamente).
     */
    @Column(name = "id_negocio", nullable = false, unique = true, length = 50)
    private String idNegocio;

    @Column(name = "cartao", nullable = false, length = 30)
    private String cartao;

    @Embedded
    private DescricaoTransacao descricao;

    @Embedded
    private FormaPagamento formaPagamento;

    @Column(name = "nsu", length = 30)
    private String nsu;

    @Column(name = "codigo_autorizacao", length = 30)
    private String codigoAutorizacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 15)
    private StatusTransacao status;

    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm;

    @Column(name = "ativo")
    private Boolean ativo = true;

    @PrePersist
    void prePersist() {
        this.criadoEm = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getIdNegocio() {
        return idNegocio;
    }

    public void setIdNegocio(String idNegocio) {
        this.idNegocio = idNegocio;
    }

    public String getCartao() {
        return cartao;
    }

    public void setCartao(String cartao) {
        this.cartao = cartao;
    }

    public DescricaoTransacao getDescricao() {
        return descricao;
    }

    public void setDescricao(DescricaoTransacao descricao) {
        this.descricao = descricao;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
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

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }
    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
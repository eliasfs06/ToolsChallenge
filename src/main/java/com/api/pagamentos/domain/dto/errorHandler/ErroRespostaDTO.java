package com.api.pagamentos.domain.dto.errorHandler;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO padronizado para respostas de erro da API.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErroRespostaDTO {

    private LocalDateTime timestamp;
    private Integer status;
    private String erro;
    private String mensagem;
    private String caminho;

    /**
     * Lista de erros por campo (preenchida apenas quando houver falha de validação).
     */
    private List<ErroCampoDTO> campos;

    public ErroRespostaDTO() {
    }

    public ErroRespostaDTO(LocalDateTime timestamp, Integer status, String erro, String mensagem, String caminho, List<ErroCampoDTO> campos) {
        this.timestamp = timestamp;
        this.status = status;
        this.erro = erro;
        this.mensagem = mensagem;
        this.caminho = caminho;
        this.campos = campos;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public List<ErroCampoDTO> getCampos() {
        return campos;
    }

    public void setCampos(List<ErroCampoDTO> campos) {
        this.campos = campos;
    }
}
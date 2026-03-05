package com.api.pagamentos.domain.mappers;

import com.api.pagamentos.domain.dto.*;
import com.api.pagamentos.domain.model.DescricaoTransacao;
import com.api.pagamentos.domain.model.FormaPagamento;
import com.api.pagamentos.domain.model.Transacao;

/**
 * Mapper responsável por converter DTOs de pagamento em entidades e vice-versa.
 */
public final class PagamentoMapper {

    private PagamentoMapper() {
    }

    /**
     * Converte a requisição em entidade.
     *
     * @param dto requisição.
     * @return entidade {@link Transacao}.
     */
    public static Transacao paraEntidade(PagamentoRequisicaoDTO dto) {
        Transacao entidade = new Transacao();
        entidade.setIdNegocio(dto.getTransacao().getId());
        entidade.setCartao(dto.getTransacao().getCartao());

        DescricaoTransacao descricao = new DescricaoTransacao();
        descricao.setValor(dto.getTransacao().getDescricao().getValor());
        descricao.setDataHora(dto.getTransacao().getDescricao().getDataHora());
        descricao.setEstabelecimento(dto.getTransacao().getDescricao().getEstabelecimento());
        entidade.setDescricao(descricao);

        FormaPagamento forma = new FormaPagamento();
        forma.setTipo(dto.getFormaPagamento().getTipo());
        forma.setParcelas(dto.getFormaPagamento().getParcelas());
        entidade.setFormaPagamento(forma);

        return entidade;
    }

    /**
     * Converte entidade em resposta.
     *
     * @param entidade transação persistida.
     * @return DTO de resposta.
     */
    public static PagamentoRespostaDTO paraResposta(Transacao entidade) {
        PagamentoRespostaDTO resposta = new PagamentoRespostaDTO();

        TransacaoDTO transacaoDTO = new TransacaoDTO();
        transacaoDTO.setCartao(entidade.getCartao());
        transacaoDTO.setId(entidade.getIdNegocio());

        DescricaoDTO descricaoDTO = new DescricaoDTO();
        descricaoDTO.setValor(entidade.getDescricao().getValor());
        descricaoDTO.setDataHora(entidade.getDescricao().getDataHora());
        descricaoDTO.setEstabelecimento(entidade.getDescricao().getEstabelecimento());

        // Campos de retorno (colocados em descricao para refletir o exemplo)
        descricaoDTO.setNsu(entidade.getNsu());
        descricaoDTO.setCodigoAutorizacao(entidade.getCodigoAutorizacao());
        descricaoDTO.setStatus(entidade.getStatus());

        transacaoDTO.setDescricao(descricaoDTO);

        FormaPagamentoDTO formaDTO = new FormaPagamentoDTO();
        formaDTO.setTipo(entidade.getFormaPagamento().getTipo());
        formaDTO.setParcelas(entidade.getFormaPagamento().getParcelas());

        resposta.setTransacao(transacaoDTO);
        resposta.setFormaPagamento(formaDTO);
        return resposta;
    }
}

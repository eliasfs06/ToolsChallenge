package com.api.pagamentos.service;

import com.api.pagamentos.domain.dto.transacao.PagamentoRespostaDTO;
import com.api.pagamentos.domain.enumeration.StatusTransacao;
import com.api.pagamentos.domain.mappers.PagamentoMapper;
import com.api.pagamentos.domain.model.Transacao;
import com.api.pagamentos.exceptions.NegocioException;
import com.api.pagamentos.repository.TransacaoRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço responsável pelo estorno de transações.
 */
@Service
public class EstornoService {

    private final TransacaoRepositorio transacaoRepositorio;

    public EstornoService(TransacaoRepositorio transacaoRepositorio) {
        this.transacaoRepositorio = transacaoRepositorio;
    }

    /**
     * Realiza o estorno de uma transação, alterando seu status para {@link StatusTransacao#CANCELADO}.
     *
     * @param requisicaoId dados do estorno.
     * @return resposta com a transação atualizada.
     */
    @Transactional
    public PagamentoRespostaDTO estornar(String requisicaoId) {

        if (requisicaoId == null || requisicaoId.trim().isBlank()) {
            throw new NegocioException("Id d transação não informado.");
        }

        Transacao transacao = transacaoRepositorio.findByIdNegocio(requisicaoId)
                .orElseThrow(() -> new NegocioException("Transação não encontrada para o id informado."));

        validaRegrasParaEstorno(transacao);

        transacao.getDescricao().setStatus(StatusTransacao.CANCELADO);

        Transacao salva = transacaoRepositorio.save(transacao);
        return PagamentoMapper.paraResposta(salva);
    }

    private void validaRegrasParaEstorno(Transacao transacao) {
        StatusTransacao statusAtual = transacao.getDescricao().getStatus();

        if (statusAtual == StatusTransacao.CANCELADO) {
            throw new NegocioException("A transação já está cancelada.");
        }
        if (statusAtual != StatusTransacao.AUTORIZADO) {
            throw new NegocioException("Somente transações autorizadas podem ser estornadas.");
        }
    }
}
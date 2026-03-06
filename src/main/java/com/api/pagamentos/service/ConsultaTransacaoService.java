package com.api.pagamentos.service;

import com.api.pagamentos.domain.dto.transacao.PagamentoRespostaDTO;
import com.api.pagamentos.domain.mappers.PagamentoMapper;
import com.api.pagamentos.domain.model.Transacao;
import com.api.pagamentos.exceptions.NegocioException;
import com.api.pagamentos.exceptions.NotFoundException;
import com.api.pagamentos.repository.TransacaoRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Serviço responsável pela consulta de transações.
 */
@Service
public class ConsultaTransacaoService {

    private final TransacaoRepositorio transacaoRepositorio;

    public ConsultaTransacaoService(TransacaoRepositorio transacaoRepositorio) {
        this.transacaoRepositorio = transacaoRepositorio;
    }

    /**
     * Consulta uma transação pelo identificador de negócio.
     *
     * @param id identificador de negócio da transação.
     * @return transação encontrada no formato de resposta.
     */
    @Transactional(readOnly = true)
    public PagamentoRespostaDTO consultarPorId(String id) {
        if (id == null || id.isBlank()) {
            throw new NegocioException("O parâmetro id é obrigatório.");
        }

        Transacao transacao = transacaoRepositorio.findByIdNegocio(id)
                .orElseThrow(() -> new NotFoundException("Transação não encontrada para o id informado."));

        return PagamentoMapper.paraResposta(transacao);
    }

    /**
     * Lista todas as transações cadastradas.
     *
     * @return lista de transações no formato de resposta.
     */
    @Transactional(readOnly = true)
    public List<PagamentoRespostaDTO> listarTodas() {
        return transacaoRepositorio.findAll()
                .stream()
                .map(PagamentoMapper::paraResposta)
                .toList();
    }
}

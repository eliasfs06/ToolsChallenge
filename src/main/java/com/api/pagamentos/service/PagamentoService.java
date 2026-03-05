package com.api.pagamentos.service;

import com.api.pagamentos.domain.dto.PagamentoRequisicaoDTO;
import com.api.pagamentos.domain.dto.PagamentoRespostaDTO;
import com.api.pagamentos.domain.enumeration.StatusTransacao;
import com.api.pagamentos.domain.enumeration.TipoFormaPagamento;
import com.api.pagamentos.domain.mappers.PagamentoMapper;
import com.api.pagamentos.domain.model.Transacao;
import com.api.pagamentos.exceptions.ConflitoException;
import com.api.pagamentos.exceptions.NegocioException;
import com.api.pagamentos.repository.TransacaoRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.SecureRandom;

/**
 * Serviço responsável pelo fluxo de pagamento.
 */
@Service
public class PagamentoService {

    private static final int TAMANHO_PADRAO_NSU = 10;
    private static final int TAMANHO_PADRAO_CODIGO_AUTORIZACAO = 10;
    private static final int VALOR_MAXIMO_TRANSACAO_AUTORIZADA = 1000;

    private final TransacaoRepositorio transacaoRepositorio;
    private final SecureRandom random = new SecureRandom();

    public PagamentoService(TransacaoRepositorio transacaoRepositorio) {
        this.transacaoRepositorio = transacaoRepositorio;
    }

    /**
     * Realiza o pagamento, validando os dados, persistindo a transação e retornando o resultado.
     *
     * @param requisicao dados do pagamento.
     * @return resposta com status e dados de retorno.
     */
    @Transactional
    public PagamentoRespostaDTO pagar(PagamentoRequisicaoDTO requisicao) {
        validarRequisicaoDePagamento(requisicao);

        String idNegocio = requisicao.getTransacao().getId();
        if (transacaoRepositorio.existsByIdNegocio(idNegocio)) {
            throw new ConflitoException("Já existe transação cadastrada com o id informado.");
        }

        Transacao transacao = PagamentoMapper.paraEntidade(requisicao);

        boolean autorizado = verificaAutorizacao(transacao.getDescricao().getValor());

        preencheEstadosDeRetornoDaTransacao(autorizado, transacao);

        Transacao salva = transacaoRepositorio.save(transacao);

        return PagamentoMapper.paraResposta(salva);
    }

    private void preencheEstadosDeRetornoDaTransacao(boolean autorizado, Transacao transacao) {
        if (autorizado) {
            transacao.getDescricao().setStatus(StatusTransacao.AUTORIZADO);
            transacao.getDescricao().setNsu(gerarNumero(TAMANHO_PADRAO_NSU));
            transacao.getDescricao().setCodigoAutorizacao(gerarNumero(TAMANHO_PADRAO_CODIGO_AUTORIZACAO));
        } else {
            transacao.getDescricao().setStatus(StatusTransacao.NEGADO);
            transacao.getDescricao().setNsu(null);
            transacao.getDescricao().setCodigoAutorizacao(null);
        }
    }

    /**
     * Decide se a transação deve ser autorizada ou negada.
     * Mantém uma regra simples e determinística para facilitar testes.
     */
    private boolean verificaAutorizacao(BigDecimal valor) {
        if (valor == null) {
            return false;
        }
        return valor.compareTo(BigDecimal.ZERO) > 0 && valor.compareTo(new BigDecimal(VALOR_MAXIMO_TRANSACAO_AUTORIZADA)) <= 0;
    }


    private void validarRequisicaoDePagamento(PagamentoRequisicaoDTO req) {
        if (req == null || req.getTransacao() == null || req.getFormaPagamento() == null) {
            throw new NegocioException("Payload inválido.");
        }

        if (req.getTransacao().getId() == null || req.getTransacao().getId().isBlank()) {
            throw new NegocioException("O campo transacao.id é obrigatório.");
        }
        if (req.getTransacao().getCartao() == null || req.getTransacao().getCartao().isBlank()) {
            throw new NegocioException("O campo transacao.cartao é obrigatório.");
        }
        if (req.getTransacao().getDescricao() == null) {
            throw new NegocioException("O campo transacao.descricao é obrigatório.");
        }
        if (req.getTransacao().getDescricao().getValor() == null) {
            throw new NegocioException("O campo transacao.descricao.valor é obrigatório.");
        }
        if (req.getTransacao().getDescricao().getDataHora() == null) {
            throw new NegocioException("O campo transacao.descricao.dataHora é obrigatório.");
        }
        if (req.getTransacao().getDescricao().getEstabelecimento() == null
                || req.getTransacao().getDescricao().getEstabelecimento().isBlank()) {
            throw new NegocioException("O campo transacao.descricao.estabelecimento é obrigatório.");
        }

        TipoFormaPagamento tipo = req.getFormaPagamento().getTipo();
        Integer parcelas = req.getFormaPagamento().getParcelas();

        if (tipo == null) {
            throw new NegocioException("O campo formaPagamento.tipo é obrigatório.");
        }
        if (parcelas == null || parcelas < 1) {
            throw new NegocioException("O campo formaPagamento.parcelas deve ser maior ou igual a 1.");
        }

        if (tipo == TipoFormaPagamento.AVISTA && parcelas != 1) {
            throw new NegocioException("Pagamento à vista deve possuir 1 parcela.");
        }
        if (tipo != TipoFormaPagamento.AVISTA && parcelas == 1) {
            throw new NegocioException("Pagamento parcelado deve possuir mais de 1 parcela.");
        }
    }

    /**
     * Gera um número aleatório com o tamanho informado.
     *
     * @param tamanho quantidade de dígitos.
     * @return string numérica.
     */
    private String gerarNumero(int tamanho) {
        StringBuilder sb = new StringBuilder(tamanho);
        for (int i = 0; i < tamanho; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
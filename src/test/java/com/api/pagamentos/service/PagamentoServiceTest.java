package com.api.pagamentos.service;

import com.api.pagamentos.domain.dto.*;
import com.api.pagamentos.domain.enumeration.StatusTransacao;
import com.api.pagamentos.domain.enumeration.TipoFormaPagamento;
import com.api.pagamentos.domain.model.Transacao;
import com.api.pagamentos.exceptions.ConflitoException;
import com.api.pagamentos.exceptions.NegocioException;
import com.api.pagamentos.repository.TransacaoRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testes unitários do serviço de pagamento.
 */
class PagamentoServiceTest {

    private static final int VALOR_MAXIMO_TRANSACAO_AUTORIZADA = 1000;
    private TransacaoRepositorio transacaoRepositorio;
    private PagamentoService pagamentoService;

    @BeforeEach
    void setup() {
        transacaoRepositorio = mock(TransacaoRepositorio.class);
        pagamentoService = new PagamentoService(transacaoRepositorio);
    }

    @Test
    void deveRecusarQuandoPayloadForNulo() {
        assertThrows(NegocioException.class, () -> pagamentoService.pagar(null));
        verifyNoInteractions(transacaoRepositorio);
    }

    @Test
    void deveRecusarQuandoCamposObrigatoriosNaoForemInformados() {
        PagamentoRequisicaoDTO req = new PagamentoRequisicaoDTO();

        assertThrows(NegocioException.class, () -> pagamentoService.pagar(req));
        verifyNoInteractions(transacaoRepositorio);
    }

    @Test
    void deveRecusarQuandoIdDaTransacaoJaExistir() {
        PagamentoRequisicaoDTO req = criarRequisicaoPadrao("T1", new BigDecimal("100"), TipoFormaPagamento.AVISTA, 1);

        when(transacaoRepositorio.existsByIdNegocio("T1")).thenReturn(true);

        assertThrows(ConflitoException.class, () -> pagamentoService.pagar(req));
        verify(transacaoRepositorio).existsByIdNegocio("T1");
        verify(transacaoRepositorio, never()).save(any());
    }

    @Test
    void deveRecusarPagamentoAVistaComParcelasDiferenteDe1() {
        PagamentoRequisicaoDTO req = criarRequisicaoPadrao("T2", new BigDecimal(VALOR_MAXIMO_TRANSACAO_AUTORIZADA), TipoFormaPagamento.AVISTA, 2);

        assertThrows(NegocioException.class, () -> pagamentoService.pagar(req));
        verifyNoInteractions(transacaoRepositorio);
    }

    @Test
    void deveRecusarPagamentoParceladoComApenas1Parcela() {
        PagamentoRequisicaoDTO req = criarRequisicaoPadrao("T3", new BigDecimal(VALOR_MAXIMO_TRANSACAO_AUTORIZADA), TipoFormaPagamento.PARCELADO_LOJA, 1);

        assertThrows(NegocioException.class, () -> pagamentoService.pagar(req));
        verifyNoInteractions(transacaoRepositorio);
    }

    @Test
    void deveAutorizarQuandoValorForMaiorQueZeroEMenorOuIgualAValorMaximoPermitido() {
        PagamentoRequisicaoDTO req = criarRequisicaoPadrao("T4", new BigDecimal(VALOR_MAXIMO_TRANSACAO_AUTORIZADA), TipoFormaPagamento.AVISTA, 1);

        when(transacaoRepositorio.existsByIdNegocio("T4")).thenReturn(false);

        when(transacaoRepositorio.save(any(Transacao.class))).thenAnswer(inv -> inv.getArgument(0));

        PagamentoRespostaDTO resp = pagamentoService.pagar(req);

        assertNotNull(resp);
        assertNotNull(resp.getTransacao());
        assertNotNull(resp.getTransacao().getDescricao());
        assertEquals(StatusTransacao.AUTORIZADO, resp.getTransacao().getDescricao().getStatus());
        assertNotNull(resp.getTransacao().getDescricao().getNsu());
        assertNotNull(resp.getTransacao().getDescricao().getCodigoAutorizacao());

        verify(transacaoRepositorio).save(any(Transacao.class));
    }

    @Test
    void deveNegarQuandoValorForMenorOuIgualAZeroOuMaiorQueValorMaximoPermitido() {
        PagamentoRequisicaoDTO req = criarRequisicaoPadrao("T5", new BigDecimal(VALOR_MAXIMO_TRANSACAO_AUTORIZADA + 1), TipoFormaPagamento.AVISTA, 1);

        when(transacaoRepositorio.existsByIdNegocio("T5")).thenReturn(false);
        when(transacaoRepositorio.save(any(Transacao.class))).thenAnswer(inv -> inv.getArgument(0));

        PagamentoRespostaDTO resp = pagamentoService.pagar(req);

        assertEquals(StatusTransacao.NEGADO, resp.getTransacao().getDescricao().getStatus());
        assertNull(resp.getTransacao().getDescricao().getNsu());
        assertNull(resp.getTransacao().getDescricao().getCodigoAutorizacao());
    }


    private PagamentoRequisicaoDTO criarRequisicaoPadrao(String id, BigDecimal valor, TipoFormaPagamento tipo, int parcelas) {
        DescricaoDTO descricao = new DescricaoDTO();
        descricao.setValor(valor);
        descricao.setDataHora(LocalDateTime.now());
        descricao.setEstabelecimento("LOJA TESTE");

        TransacaoDTO transacaoDTO = new TransacaoDTO();
        transacaoDTO.setId(id);
        transacaoDTO.setCartao("1234123412341234");
        transacaoDTO.setDescricao(descricao);

        FormaPagamentoDTO forma = new FormaPagamentoDTO();
        forma.setTipo(tipo);
        forma.setParcelas(parcelas);

        PagamentoRequisicaoDTO req = new PagamentoRequisicaoDTO();
        req.setTransacao(transacaoDTO);
        req.setFormaPagamento(forma);
        return req;
    }
}
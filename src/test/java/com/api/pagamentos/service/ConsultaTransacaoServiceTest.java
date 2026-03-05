package com.api.pagamentos.service;

import com.api.pagamentos.domain.dto.PagamentoRespostaDTO;
import com.api.pagamentos.domain.enumeration.StatusTransacao;
import com.api.pagamentos.domain.enumeration.TipoFormaPagamento;
import com.api.pagamentos.domain.model.DescricaoTransacao;
import com.api.pagamentos.domain.model.FormaPagamento;
import com.api.pagamentos.domain.model.Transacao;
import com.api.pagamentos.exceptions.NegocioException;
import com.api.pagamentos.repository.TransacaoRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testes unitários do serviço de consulta de transações.
 */
class ConsultaTransacaoServiceTest {

    private TransacaoRepositorio transacaoRepositorio;
    private ConsultaTransacaoService consultaServico;

    @BeforeEach
    void setup() {
        transacaoRepositorio = mock(TransacaoRepositorio.class);
        consultaServico = new ConsultaTransacaoService(transacaoRepositorio);
    }

    @Test
    void deveRecusarConsultaPorIdQuandoIdNaoForInformado() {
        assertThrows(NegocioException.class, () -> consultaServico.consultarPorId(" "));
        verifyNoInteractions(transacaoRepositorio);
    }

    @Test
    void deveFalharQuandoNaoEncontrarTransacaoPorId() {
        when(transacaoRepositorio.findByIdNegocio("T1")).thenReturn(Optional.empty());

        assertThrows(NegocioException.class, () -> consultaServico.consultarPorId("T1"));
        verify(transacaoRepositorio).findByIdNegocio("T1");
    }

    @Test
    void deveRetornarTransacaoQuandoEncontrarPorId() {
        Transacao t = criarTransacao("T2", StatusTransacao.AUTORIZADO);
        when(transacaoRepositorio.findByIdNegocio("T2")).thenReturn(Optional.of(t));

        PagamentoRespostaDTO resp = consultaServico.consultarPorId("T2");

        assertNotNull(resp);
        assertEquals("T2", resp.getTransacao().getId());
        assertEquals(StatusTransacao.AUTORIZADO, resp.getTransacao().getDescricao().getStatus());
    }

    @Test
    void deveListarTodasAsTransacoes() {
        List<Transacao> transacoes = List.of(
                criarTransacao("T3", StatusTransacao.AUTORIZADO),
                criarTransacao("T4", StatusTransacao.NEGADO)
        );

        when(transacaoRepositorio.findAll()).thenReturn(transacoes);

        List<PagamentoRespostaDTO> resp = consultaServico.listarTodas();

        assertNotNull(resp);
        assertEquals(2, resp.size());
        assertEquals("T3", resp.get(0).getTransacao().getId());
        assertEquals("T4", resp.get(1).getTransacao().getId());
    }

    private Transacao criarTransacao(String idNegocio, StatusTransacao status) {
        DescricaoTransacao desc = new DescricaoTransacao();
        desc.setValor(new BigDecimal("100"));
        desc.setDataHora(LocalDateTime.now());
        desc.setEstabelecimento("LOJA");
        desc.setStatus(status);

        FormaPagamento forma = new FormaPagamento();
        forma.setTipo(TipoFormaPagamento.AVISTA);
        forma.setParcelas(1);

        Transacao t = new Transacao();
        t.setIdNegocio(idNegocio);
        t.setCartao("1234123412341234");
        t.setDescricao(desc);
        t.setFormaPagamento(forma);
        return t;
    }
}

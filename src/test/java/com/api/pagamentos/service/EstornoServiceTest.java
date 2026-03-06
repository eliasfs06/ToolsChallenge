package com.api.pagamentos.service;

import com.api.pagamentos.domain.dto.transacao.PagamentoRespostaDTO;
import com.api.pagamentos.domain.enumeration.StatusTransacao;
import com.api.pagamentos.domain.enumeration.TipoFormaPagamento;
import com.api.pagamentos.domain.model.DescricaoTransacao;
import com.api.pagamentos.domain.model.FormaPagamento;
import com.api.pagamentos.domain.model.Transacao;
import com.api.pagamentos.exceptions.NegocioException;
import com.api.pagamentos.repository.TransacaoRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testes unitários do serviço de estorno.
 */
class EstornoServiceTest {

    private TransacaoRepositorio transacaoRepositorio;
    private EstornoService estornoService;

    @BeforeEach
    void setup() {
        transacaoRepositorio = mock(TransacaoRepositorio.class);
        estornoService = new EstornoService(transacaoRepositorio);
    }

    @Test
    void deveRecusarQuandoIdNaoForInformado() {
        assertThrows(NegocioException.class, () -> estornoService.estornar(""));
        verifyNoInteractions(transacaoRepositorio);
    }

    @Test
    void deveFalharQuandoTransacaoNaoExistir() {
        String transacaoId ="T1";

        when(transacaoRepositorio.findByIdNegocio(transacaoId)).thenReturn(Optional.empty());

        assertThrows(NegocioException.class, () -> estornoService.estornar(transacaoId));
        verify(transacaoRepositorio).findByIdNegocio(transacaoId);
        verify(transacaoRepositorio, never()).save(any());
    }

    @Test
    void deveRecusarEstornoQuandoStatusForNegado() {
        String transacaoId ="T2";
        Transacao t = criarTransacao(transacaoId, StatusTransacao.NEGADO);

        when(transacaoRepositorio.findByIdNegocio(transacaoId)).thenReturn(Optional.of(t));

        assertThrows(NegocioException.class, () -> estornoService.estornar(transacaoId));
        verify(transacaoRepositorio, never()).save(any());
    }

    @Test
    void deveRecusarEstornoQuandoStatusJaForCancelado() {
        String transacaoId ="T3";
        Transacao t = criarTransacao(transacaoId, StatusTransacao.CANCELADO);

        when(transacaoRepositorio.findByIdNegocio(transacaoId)).thenReturn(Optional.of(t));

        assertThrows(NegocioException.class, () -> estornoService.estornar(transacaoId));
        verify(transacaoRepositorio, never()).save(any());
    }

    @Test
    void deveEstornarQuandoStatusForAutorizado() {
        String transacaoId ="T4";
        Transacao t = criarTransacao(transacaoId, StatusTransacao.AUTORIZADO);

        when(transacaoRepositorio.findByIdNegocio(transacaoId)).thenReturn(Optional.of(t));
        when(transacaoRepositorio.save(any(Transacao.class))).thenAnswer(inv -> inv.getArgument(0));

        PagamentoRespostaDTO resp = estornoService.estornar(transacaoId);

        assertNotNull(resp);
        assertEquals(StatusTransacao.CANCELADO, resp.getTransacao().getDescricao().getStatus());

        ArgumentCaptor<Transacao> captor = ArgumentCaptor.forClass(Transacao.class);
        verify(transacaoRepositorio).save(captor.capture());
        assertEquals(StatusTransacao.CANCELADO, captor.getValue().getDescricao().getStatus());
    }

    private Transacao criarTransacao(String idNegocio, StatusTransacao status) {
        DescricaoTransacao desc = new DescricaoTransacao();
        desc.setValor(new BigDecimal("100"));
        desc.setDataHora(LocalDateTime.now());
        desc.setEstabelecimento("LOJA");
        desc.setStatus(status);
        desc.setNsu("123");
        desc.setCodigoAutorizacao("999");

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

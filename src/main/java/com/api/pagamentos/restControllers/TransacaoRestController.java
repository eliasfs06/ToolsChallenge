package com.api.pagamentos.restControllers;

import com.api.pagamentos.domain.dto.PagamentoRequisicaoDTO;
import com.api.pagamentos.domain.dto.PagamentoRespostaDTO;
import com.api.pagamentos.service.ConsultaTransacaoService;
import com.api.pagamentos.service.EstornoService;
import com.api.pagamentos.service.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller responsável por expor as operações de pagamento, consulta e estorno de transações.
 */
@RestController
@RequestMapping("/transacoes")
public class TransacaoRestController {

    private final PagamentoService pagamentoService;
    private final ConsultaTransacaoService consultaTransacaoService;
    private final EstornoService estornoService;

    public TransacaoRestController(
            PagamentoService pagamentoService,
            ConsultaTransacaoService consultaTransacaoService,
            EstornoService estornoService
    ) {
        this.pagamentoService = pagamentoService;
        this.consultaTransacaoService = consultaTransacaoService;
        this.estornoService = estornoService;
    }

    /**
     * Realiza uma operação de pagamento.
     *
     * @param requisicao dados do pagamento.
     * @return transação processada com o resultado (status, nsu e código de autorização quando aplicável).
     */
    @PostMapping("/pagar")
    public ResponseEntity<PagamentoRespostaDTO> pagar(@Valid @RequestBody PagamentoRequisicaoDTO requisicao) {
        PagamentoRespostaDTO resposta = pagamentoService.pagar(requisicao);
        return ResponseEntity.status(HttpStatus.CREATED).body(resposta);
    }

    /**
     * Consulta uma transação pelo identificador de negócio.
     *
     * @param id identificador de negócio da transação.
     * @return transação encontrada.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PagamentoRespostaDTO> consultarPorId(@PathVariable("id") String id) {
        PagamentoRespostaDTO resposta = consultaTransacaoService.consultarPorId(id);
        return ResponseEntity.ok(resposta);
    }

    /**
     * Lista todas as transações cadastradas.
     *
     * @return lista de transações.
     */
    @GetMapping("/listar")
    public ResponseEntity<List<PagamentoRespostaDTO>> listarTodas() {
        List<PagamentoRespostaDTO> resposta = consultaTransacaoService.listarTodas();
        return ResponseEntity.ok(resposta);
    }

    /**
     * Realiza o estorno de uma transação.
     *
     * @param id identificador de negócio da transação.
     * @return transação após o estorno (status CANCELADO).
     */
    @PostMapping("/{id}/estorno")
    public ResponseEntity<PagamentoRespostaDTO> estornar(@PathVariable("id") String id) {
        PagamentoRespostaDTO resposta = estornoService.estornar(id);
        return ResponseEntity.ok(resposta);
    }
}

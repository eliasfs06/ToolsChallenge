package com.api.pagamentos.exceptions;

import com.api.pagamentos.domain.dto.ErroCampoDTO;
import com.api.pagamentos.domain.dto.ErroRespostaDTO;
import com.api.pagamentos.exceptions.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Handler global de exceções para padronizar respostas de erro da API.
 */
@RestControllerAdvice
public class ApiExceptionHandler {

    /**
     * Trata violações de regras de negócio.
     *
     * @param ex exceção de regra de negócio.
     * @param request requisição HTTP.
     * @return resposta 400 com payload de erro padronizado.
     */
    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<ErroRespostaDTO> tratarRegraNegocio(NegocioException ex, HttpServletRequest request) {
        return criarErro(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI(), null);
    }

    /**
     * Trata conflitos de dados (ex.: id duplicado).
     *
     * @param ex exceção de conflito.
     * @param request requisição HTTP.
     * @return resposta 409 com payload de erro padronizado.
     */
    @ExceptionHandler(ConflitoException.class)
    public ResponseEntity<ErroRespostaDTO> tratarConflito(ConflitoException ex, HttpServletRequest request) {
        return criarErro(HttpStatus.CONFLICT, ex.getMessage(), request.getRequestURI(), null);
    }

    /**
     * Trata quando um recurso não é encontrado.
     *
     * @param ex exceção de recurso não encontrado.
     * @param request requisição HTTP.
     * @return resposta 404 com payload de erro padronizado.
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErroRespostaDTO> tratarNaoEncontrado(NotFoundException ex, HttpServletRequest request) {
        return criarErro(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI(), null);
    }

    /**
     * Trata erros de validação do Bean Validation em {@code @RequestBody} com {@code @Valid}.
     *
     * @param ex exceção de validação.
     * @param request requisição HTTP.
     * @return resposta 400 com lista de campos inválidos.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroRespostaDTO> tratarValidacaoBody(MethodArgumentNotValidException ex,
                                                               HttpServletRequest request) {

        List<ErroCampoDTO> campos = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::paraErroCampo)
                .toList();

        return criarErro(
                HttpStatus.BAD_REQUEST,
                "Falha de validação nos campos informados.",
                request.getRequestURI(),
                campos
        );
    }

    /**
     * Trata violações de validação em {@code @PathVariable} / {@code @RequestParam}
     * quando o controller está anotado com {@code @Validated}.
     *
     * @param ex exceção de violação de constraints.
     * @param request requisição HTTP.
     * @return resposta 400 com payload de erro padronizado.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErroRespostaDTO> tratarValidacaoParametros(ConstraintViolationException ex,
                                                                     HttpServletRequest request) {
        return criarErro(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI(), null);
    }

    /**
     * Trata erros não mapeados.
     *
     * @param ex exceção genérica.
     * @param request requisição HTTP.
     * @return resposta 500 com payload padronizado.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroRespostaDTO> tratarGenerico(Exception ex, HttpServletRequest request) {
        return criarErro(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado.", request.getRequestURI(), null);
    }

    private ResponseEntity<ErroRespostaDTO> criarErro(HttpStatus status,
                                                      String mensagem,
                                                      String caminho,
                                                      List<ErroCampoDTO> campos) {

        ErroRespostaDTO body = new ErroRespostaDTO(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                mensagem,
                caminho,
                campos
        );

        return ResponseEntity.status(status).body(body);
    }

    private ErroCampoDTO paraErroCampo(FieldError erro) {
        return new ErroCampoDTO(erro.getField(), erro.getDefaultMessage());
    }
}
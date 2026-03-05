package com.api.pagamentos.repository;

import com.api.pagamentos.domain.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositório de acesso a dados de {@link Transacao}.
 */
public interface TransacaoRepositorio extends JpaRepository<Transacao, Long> {

    /**
     * Busca uma transação pelo identificador de negócio.
     *
     * @param idNegocio identificador da transação informado no payload.
     * @return transação encontrada (se existir).
     */
    Optional<Transacao> findByIdNegocio(String idNegocio);

    /**
     * Verifica se já existe transação com o identificador de negócio informado.
     *
     * @param idNegocio identificador da transação informado no payload.
     * @return true se já existir, caso contrário false.
     */
    boolean existsByIdNegocio(String idNegocio);
}
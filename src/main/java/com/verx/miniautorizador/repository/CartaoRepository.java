package com.verx.miniautorizador.repository;

import com.verx.miniautorizador.core.model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartaoRepository extends JpaRepository<Cartao, String> {
    @Override
    Optional<Cartao> findById(String s);

    Optional<Cartao> findByNumero(String numeroCartao);
}


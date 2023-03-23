package com.verx.miniautorizador.service;

import com.verx.miniautorizador.core.dto.CartaoDTO;
import com.verx.miniautorizador.core.model.Cartao;
import com.verx.miniautorizador.repository.CartaoRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartaoService {
    private final CartaoRepository cartaoRepository;
    private final String CACHE_KEY = "Cartao";

    public CartaoService(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    @CacheEvict(CACHE_KEY)
    public Cartao criar(CartaoDTO card) {
        Cartao cartao = cartaoRepository.save(Cartao.builder().from(card).build());
        return cartao;
    }

    @Cacheable(CACHE_KEY)
    public Double getSaldo(String numeroCartao) {
        Optional<Cartao> cartao = cartaoRepository.findByNumero(numeroCartao);
        return (cartao.isPresent()) ? cartao.get().getSaldo() : null;
    }

    @Cacheable(CACHE_KEY)
    public Optional<Cartao> buscarPorNumero(String numero) {
        return cartaoRepository.findByNumero(numero);
    }
}


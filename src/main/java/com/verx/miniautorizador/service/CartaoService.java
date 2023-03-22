package com.verx.miniautorizador.service;

import com.verx.miniautorizador.core.dto.CartaoDTO;
import com.verx.miniautorizador.core.model.Cartao;
import com.verx.miniautorizador.repository.CartaoRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartaoService {
    private final CartaoRepository cartaoRepository;
    private final String CACHE_KEY = "cartao";
    private final RedisTemplate<String, Cartao> redisTemplate;

    public CartaoService(CartaoRepository cartaoRepository, RedisTemplate<String, Cartao> redisTemplate) {
        this.cartaoRepository = cartaoRepository;
        this.redisTemplate = redisTemplate;
    }

    public Cartao criar(CartaoDTO card) {
        Cartao cartao = cartaoRepository.save(Cartao.builder().from(card).build());
        redisTemplate.opsForValue().set(CACHE_KEY + cartao.getNumero(), cartao);
        return cartao;
    }

    @Cacheable(value = CACHE_KEY, key = "#numeroCartao")
    public Double getSaldo(String numeroCartao) {
        Optional<Cartao> cartao = cartaoRepository.findByNumero(numeroCartao);
        return (cartao.isPresent()) ? cartao.get().getSaldo() : null;
    }

    @Cacheable(value = CACHE_KEY, key = "#numero")
    public Optional<Cartao> buscarPorNumero(String numero) {
        return cartaoRepository.findByNumero(numero);
    }
}


package com.verx.miniautorizador.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.verx.miniautorizador.core.dto.CartaoDTO;
import com.verx.miniautorizador.core.model.Cartao;
import com.verx.miniautorizador.repository.CartaoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
@RunWith(MockitoJUnitRunner.class)
public class CartaoServiceTest {
    @InjectMocks
    private CartaoService cartaoService;

    @Mock
    private CartaoRepository cartaoRepository;

    @Test
    public void deveCriarCartao() {
        CartaoDTO card = new CartaoDTO();
        card.setNumero("1234567890123456");
        card.setSenha("1234");

        Cartao cartao = new Cartao();
        cartao.setNumero("1234567890123456");
        cartao.setSenha("1234");
        cartao.setSaldo(500.00);

        when(cartaoRepository.save(Cartao.builder().from(card).build())).thenReturn(cartao);

        Cartao criado = cartaoService.criar(card);

        assertNotNull(criado);
        assertEquals(criado.getNumero(), card.getNumero());
        assertEquals(criado.getSenha(), card.getSenha());
        assertEquals(criado.getSaldo(), 500.00, 0.0);
    }

    @Test
    public void deveRetornarSaldo() {
        String numeroCartao = "1234567890123456";

        Cartao cartao = new Cartao();
        cartao.setNumero("1234567890123456");
        cartao.setSenha("1234");
        cartao.setSaldo(500.00);

        when(cartaoRepository.findByNumero(numeroCartao)).thenReturn(Optional.of(cartao));

        Double saldo = cartaoService.getSaldo(numeroCartao);

        assertNotNull(saldo);
        assertEquals(saldo, 500.00, 0.0);
    }

    @Test
    public void deveRetornarOptionalVazioSeCartaoNaoExiste() {
        String numeroCartao = "1234567890123456";

        when(cartaoRepository.findByNumero(numeroCartao)).thenReturn(Optional.empty());

        Double saldo = cartaoService.getSaldo(numeroCartao);

        assertTrue(saldo == null);
    }

    @Test
    public void deveRetornarCartao() {
        String numeroCartao = "1234567890123456";

        Cartao cartao = new Cartao();
        cartao.setNumero(numeroCartao);
        cartao.setSenha("1234");
        cartao.setSaldo(500.00);

        when(cartaoRepository.findByNumero(numeroCartao)).thenReturn(Optional.of(cartao));

        Optional<Cartao> criado = cartaoService.buscarPorNumero(numeroCartao);

        assertTrue(criado.isPresent());
        assertEquals(criado.get().getNumero(), cartao.getNumero());
        assertEquals(criado.get().getSenha(), cartao.getSenha());
        assertEquals(criado.get().getSaldo(), cartao.getSaldo(), 0.0);
    }
}
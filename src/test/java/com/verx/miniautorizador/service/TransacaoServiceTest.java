package com.verx.miniautorizador.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.verx.miniautorizador.core.StatusTransacao;
import com.verx.miniautorizador.core.dto.TransacaoCartaoDTO;
import com.verx.miniautorizador.core.model.Cartao;
import com.verx.miniautorizador.core.strategy.transacao.AutorizarTransacaoStrategy;
import com.verx.miniautorizador.repository.CartaoRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TransacaoServiceTest {

    @InjectMocks
    private TransacaoService transacaoService;

    @Mock
    private CartaoRepository cartaoRepository;

    private List<AutorizarTransacaoStrategy> estrategiasValidacao;

    @Before
    public void setup() {
        estrategiasValidacao = new ArrayList<>();
        transacaoService = new TransacaoService(cartaoRepository, estrategiasValidacao);
    }

    @Test
    public void testAutorizarTransacaoSucesso() {
        TransacaoCartaoDTO transacao = new TransacaoCartaoDTO();
        transacao.setNumeroCartao("1234567890123456");
        transacao.setSenhaCartao("1234");
        transacao.setValor(100.00);

        Cartao cartao = new Cartao();
        cartao.setNumero("1234567890123456");
        cartao.setSenha("1234");
        cartao.setSaldo(500.00);

        Mockito.when(cartaoRepository.findByNumero(Mockito.anyString()))
                .thenReturn(java.util.Optional.of(cartao));

        StatusTransacao status = transacaoService.autorizarTransacao(transacao);

        assertEquals(StatusTransacao.OK, status);
        Mockito.verify(cartaoRepository).save(cartao);
    }

    @Test
    public void testAutorizarTransacaoEstrategiaRecusada() {
        TransacaoCartaoDTO transacao = new TransacaoCartaoDTO();
        transacao.setNumeroCartao("1234567890123456");
        transacao.setSenhaCartao("1234");
        transacao.setValor(100.00);

        Cartao cartao = new Cartao();
        cartao.setNumero("1234567890123456");
        cartao.setSenha("1234");
        cartao.setSaldo(300.00);

        Mockito.when(cartaoRepository.findByNumero("1234567890123456")).thenReturn(Optional.of(cartao));

        StatusTransacao status = transacaoService.autorizarTransacao(transacao);

        assertEquals(StatusTransacao.SALDO_INSUFICIENTE, status);
        Mockito.verify(cartaoRepository, Mockito.never()).save(cartao);
    }
}

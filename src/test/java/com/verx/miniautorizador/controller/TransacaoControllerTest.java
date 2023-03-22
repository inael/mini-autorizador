package com.verx.miniautorizador.controller;

import com.verx.miniautorizador.core.StatusTransacao;
import com.verx.miniautorizador.core.dto.TransacaoCartaoDTO;
import com.verx.miniautorizador.service.TransacaoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(TransacaoController.class)
public class TransacaoControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private TransacaoService transacaoService;

    @Test
    public void autorizarTransacaoComSucesso() throws Exception {
        TransacaoCartaoDTO transacao = TransacaoCartaoDTO
                .builder()
                .numeroCartao("123456789")
                .senhaCartao("123")
                .valor(10.0).build();

        when(transacaoService.autorizarTransacao(transacao)).thenReturn(StatusTransacao.OK);

        mvc.perform(post("/transacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(transacao)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", is("OK")));
    }

    @Test
    public void autorizarTransacaoComSenhaInvalida() throws Exception {
        TransacaoCartaoDTO transacao = TransacaoCartaoDTO
                .builder()
                .numeroCartao("123456789")
                .senhaCartao("321")
                .valor(10.0)
                .build();

        when(transacaoService.autorizarTransacao(transacao)).thenReturn(StatusTransacao.SENHA_INVALIDA);

        mvc.perform(post("/transacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(transacao)))
                .andExpect(status().is(422))
                .andExpect(jsonPath("$", is("SENHA_INVALIDA")));
    }

    @Test
    public void autorizarTransacaoComCartaoInexistente() throws Exception {
        TransacaoCartaoDTO transacao = TransacaoCartaoDTO.builder()
                .numeroCartao("987654321")
                .senhaCartao("123")
                .valor(10.0).build();

        when(transacaoService.autorizarTransacao(transacao)).thenReturn(StatusTransacao.CARTAO_INEXISTENTE);

        mvc.perform(post("/transacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(transacao)))
                .andExpect(status().is(422))
                .andExpect(jsonPath("$", is("CARTAO_INEXISTENTE")));
    }

    @Test
    public void autorizarTransacaoComSaldoInsuficiente() throws Exception {
        TransacaoCartaoDTO transacao = TransacaoCartaoDTO.builder()
                .numeroCartao("123456789")
                .senhaCartao("123")
                .valor(100.0).build();

        when(transacaoService.autorizarTransacao(transacao)).thenReturn(StatusTransacao.SALDO_INSUFICIENTE);

        mvc.perform(post("/transacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(transacao)))
                .andExpect(status().is(422))
                .andExpect(jsonPath("$", is("SALDO_INSUFICIENTE")));
    }
}

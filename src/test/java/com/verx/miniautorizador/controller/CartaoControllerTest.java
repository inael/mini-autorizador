package com.verx.miniautorizador.controller;
import com.verx.miniautorizador.core.dto.CartaoDTO;
import com.verx.miniautorizador.core.model.Cartao;
import com.verx.miniautorizador.service.CartaoService;
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
@WebMvcTest(CartaoController.class)
public class CartaoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CartaoService cartaoService;

    @Test
    public void deveCriarUmCartao() throws Exception {
        CartaoDTO cartaoDTO = CartaoDTO.builder()
                .numero("1234567890123456")
                .senha("1234")
                .build();

        Cartao cartao =  new Cartao();
        cartao.setNumero(cartaoDTO.getNumero());
        cartao.setSenha(cartaoDTO.getSenha());
        cartao.setSaldo(500.00);

        when(cartaoService.criar(cartaoDTO)).thenReturn(cartao);

        mvc.perform(post("/cartoes")
                        .content(asJsonString(cartaoDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numero", is(cartao.getNumero())))
                .andExpect(jsonPath("$.senha", is(cartao.getSenha())));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

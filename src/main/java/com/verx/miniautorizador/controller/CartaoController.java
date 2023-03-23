package com.verx.miniautorizador.controller;

import com.verx.miniautorizador.core.dto.CartaoDTO;
import com.verx.miniautorizador.core.model.Cartao;
import com.verx.miniautorizador.service.CartaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "Mini-autorizador - Transação", description = "API de cadastro de Cartão")

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;

    @Operation(summary = "Cria um cartão (todo cartão é  criado com um saldo inicial de R$500,00)")
    @PostMapping
    public ResponseEntity<CartaoDTO> criarCartao(@RequestBody Cartao cartao) {
        Optional<Cartao> cartaoEncontrado = cartaoService.buscarPorNumero(cartao.getNumero());
        if (cartaoEncontrado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(CartaoDTO.builder().fromCartao(cartao).build());
        }
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(CartaoDTO.builder().fromCartao(cartaoEncontrado.get()).build());

    }

    @GetMapping("/{numeroCartao}")
    @Operation(summary = "Obtem o saldo do cartão")
    public ResponseEntity<Double> getSaldo(@PathParam(value = "numeroCartao") String numeroCartao) {
        Double saldo = cartaoService.getSaldo(numeroCartao);
        if (saldo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(saldo);
    }

}


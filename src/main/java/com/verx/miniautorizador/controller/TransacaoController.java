package com.verx.miniautorizador.controller;

import com.verx.miniautorizador.core.StatusTransacao;
import com.verx.miniautorizador.core.dto.TransacaoCartaoDTO;
import com.verx.miniautorizador.service.TransacaoService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="Mini-autorizador - Transação",description = "API de Autorização de Transações com Cartão")
@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @Operation(summary = "Autoriza uma transação com cartão")
    @PostMapping
    public ResponseEntity autorizarTranzacao(@RequestBody TransacaoCartaoDTO cartao) {
        StatusTransacao statusTransacao = transacaoService.autorizarTransacao(cartao);
        return ResponseEntity
                .status(statusTransacao.getHttpStatus())
                .body(statusTransacao.name());

    }
}


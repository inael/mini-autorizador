package com.verx.miniautorizador.controller;

import com.verx.miniautorizador.core.StatusTransacao;
import com.verx.miniautorizador.core.dto.TransacaoCartaoDTO;
import com.verx.miniautorizador.service.TransacaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value="API de Autorização de Transações com Cartão")
@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @ApiOperation(value = "Autoriza uma transação com cartão")
    @PostMapping
    public ResponseEntity autorizarTranzacao(@RequestBody TransacaoCartaoDTO cartao) {
        StatusTransacao statusTransacao = transacaoService.autorizarTransacao(cartao);
        return ResponseEntity
                .status(statusTransacao.getHttpStatus())
                .body(statusTransacao.name());

    }
}


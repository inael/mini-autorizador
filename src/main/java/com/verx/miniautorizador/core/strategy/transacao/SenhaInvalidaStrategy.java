package com.verx.miniautorizador.core.strategy.transacao;

import com.verx.miniautorizador.core.StatusTransacao;
import com.verx.miniautorizador.core.dto.TransacaoCartaoDTO;
import com.verx.miniautorizador.core.model.Cartao;

public class SenhaInvalidaStrategy extends AutorizarTransacaoStrategy {
    @Override
    public boolean verify(Cartao cartao, TransacaoCartaoDTO transacao) {
        return !cartao.getSenha().equals(transacao.getSenhaCartao());
    }

    @Override
    public StatusTransacao getStatusTransacao() {
        return StatusTransacao.SENHA_INVALIDA;
    }
}



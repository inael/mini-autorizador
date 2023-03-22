package com.verx.miniautorizador.core.strategy.transacao;

import com.verx.miniautorizador.core.StatusTransacao;
import com.verx.miniautorizador.core.dto.TransacaoCartaoDTO;
import com.verx.miniautorizador.core.model.Cartao;

public class SaldoInsuficienteStrategy extends AutorizarTransacaoStrategy {
    @Override
    public boolean verify(Cartao cartao, TransacaoCartaoDTO transacao) {
        return cartao.getSaldo() >= transacao.getValor();
    }

    @Override
    public StatusTransacao getStatusTransacao() {
        return StatusTransacao.SALDO_INSUFICIENTE;
    }
}

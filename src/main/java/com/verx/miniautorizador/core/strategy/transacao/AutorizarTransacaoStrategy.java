package com.verx.miniautorizador.core.strategy.transacao;

import com.verx.miniautorizador.core.StatusTransacao;
import com.verx.miniautorizador.core.dto.TransacaoCartaoDTO;
import com.verx.miniautorizador.core.model.Cartao;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AutorizarTransacaoStrategy {
    private StatusTransacao statusTransacao;
    public int prioridade(){
        return 0;
    }
    public abstract boolean verify(Cartao cartao, TransacaoCartaoDTO transacao);
    public abstract StatusTransacao getStatusTransacao();
}

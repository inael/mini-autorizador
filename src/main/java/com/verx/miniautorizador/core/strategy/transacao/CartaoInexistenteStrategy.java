package com.verx.miniautorizador.core.strategy.transacao;

import com.verx.miniautorizador.core.StatusTransacao;
import com.verx.miniautorizador.core.dto.TransacaoCartaoDTO;
import com.verx.miniautorizador.core.model.Cartao;
import com.verx.miniautorizador.repository.CartaoRepository;

public class CartaoInexistenteStrategy extends AutorizarTransacaoStrategy {
    private CartaoRepository cartaoRepository;

    public CartaoInexistenteStrategy(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    @Override
    public boolean verify(Cartao cartao, TransacaoCartaoDTO transacao) {
        return !cartaoRepository.findByNumero(transacao.getNumeroCartao()).isPresent();
    }

    @Override
    public int prioridade(){
        return Integer.MAX_VALUE;
    }
    @Override
    public StatusTransacao getStatusTransacao() {
        return StatusTransacao.CARTAO_INEXISTENTE;
    }


}


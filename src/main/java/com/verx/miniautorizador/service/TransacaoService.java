package com.verx.miniautorizador.service;

import com.verx.miniautorizador.core.StatusTransacao;
import com.verx.miniautorizador.core.dto.TransacaoCartaoDTO;
import com.verx.miniautorizador.core.model.Cartao;
import com.verx.miniautorizador.core.strategy.transacao.AutorizarTransacaoStrategy;
import com.verx.miniautorizador.repository.CartaoRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class TransacaoService {

    private final CartaoRepository cartaoRepository;
    private List<AutorizarTransacaoStrategy> estrategiasValidacao;
    public TransacaoService(CartaoRepository cartaoRepository, List<AutorizarTransacaoStrategy> estrategiasValidacao) {
        this.estrategiasValidacao = estrategiasValidacao;
        this.estrategiasValidacao.sort(Comparator.comparingInt(AutorizarTransacaoStrategy::prioridade));
        this.cartaoRepository = cartaoRepository;
    }


    public StatusTransacao autorizarTransacao(TransacaoCartaoDTO transacao) {
        Optional<Cartao> cartao = cartaoRepository.findByNumero(transacao.getNumeroCartao());
        for (AutorizarTransacaoStrategy estrategia : estrategiasValidacao) {
            if (estrategia.verify(cartao.get(), transacao)) {
                return estrategia.getStatusTransacao();
            }
        }
        //garanti que 2 transações disparadas ao mesmo tempo não causem problemas relacionados à concorrência
        synchronized (cartao.get()){
            cartao.get().setSaldo(cartao.get().getSaldo() - transacao.getValor());
            cartaoRepository.save(cartao.get());
        }

        return StatusTransacao.OK;
    }
}


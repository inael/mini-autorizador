package com.verx.miniautorizador.core.dto;

import com.verx.miniautorizador.core.model.Cartao;
import lombok.*;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartaoDTO {
    private String numero;
    private String senha;

    public static class CartaoDTOBuilder {
        public CartaoDTOBuilder fromCartao(Cartao cartao) {
            this.numero = cartao.getNumero();
            this.senha = cartao.getSenha();
            return this;
        }

    }
}

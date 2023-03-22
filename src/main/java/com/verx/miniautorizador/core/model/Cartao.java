package com.verx.miniautorizador.core.model;

import com.verx.miniautorizador.core.dto.CartaoDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.springframework.data.annotation.Id;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numero;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private Double saldo = 500.0;
    public static class CartaoBuilder {
        public Cartao.CartaoBuilder from(CartaoDTO cartao) {
            this.numero = cartao.getNumero();
            this.senha = cartao.getSenha();
            return this;
        }

    }
}


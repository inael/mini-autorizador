package com.verx.miniautorizador.core.dto;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoCartaoDTO {
    private String numeroCartao;
    private String senhaCartao;
    private Double valor;
}

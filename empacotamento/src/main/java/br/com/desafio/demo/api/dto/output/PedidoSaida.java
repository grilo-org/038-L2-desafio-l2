package br.com.desafio.demo.api.dto.output;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PedidoSaida(
        @JsonProperty("pedido_id") String pedidoId,
        List<CaixaSaida> caixas) {
}

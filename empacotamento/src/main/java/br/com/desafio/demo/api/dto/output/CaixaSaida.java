package br.com.desafio.demo.api.dto.output;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public record CaixaSaida(
        @JsonProperty("caixa_id") String caixaId,
        List<String> produtos,
        @JsonInclude(JsonInclude.Include.NON_NULL) String observacao) {
}
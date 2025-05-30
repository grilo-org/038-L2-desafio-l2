package br.com.desafio.demo.api.dto.input;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record PedidoEntrada(
        @JsonAlias("pedido_id") @NotBlank String pedidoId,
        @NotEmpty @Valid List<ProdutoEntrada> produtos) {
}
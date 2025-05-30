package br.com.desafio.demo.api.dto.input;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.desafio.demo.domain.vo.Dimensao;

public record ProdutoEntrada(@JsonProperty("produto_id") String produtoId, Dimensao dimensoes) {
}
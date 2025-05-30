package br.com.desafio.demo.api.mapper;

import org.springframework.stereotype.Component;

import br.com.desafio.demo.api.dto.input.ProdutoEntrada;
import br.com.desafio.demo.domain.Produto;

@Component
public class ProdutoMapper {

    public Produto toProduto(ProdutoEntrada produtoEntrada) {
        return new Produto(produtoEntrada.produtoId(), produtoEntrada.dimensoes());
    }
}

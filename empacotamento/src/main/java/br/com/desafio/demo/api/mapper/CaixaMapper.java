package br.com.desafio.demo.api.mapper;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.desafio.demo.api.dto.output.CaixaSaida;
import br.com.desafio.demo.domain.Caixa;
import br.com.desafio.demo.domain.Produto;

@Component
public class CaixaMapper {

    public CaixaSaida toCaixaSaida(Caixa caixa) {
        return caixa.getProdutos().stream().map(Produto::getId)
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        produtoIdCollected -> new CaixaSaida(
                                caixa.getId(),
                                produtoIdCollected,
                                caixa.getObservacao())));
    }
}

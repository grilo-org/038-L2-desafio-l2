package br.com.desafio.demo.domain;

import java.util.Optional;

import br.com.desafio.demo.domain.vo.Dimensao;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Produto {
    @NotBlank
    private String id;
    @NotNull
    @Valid
    private Dimensao dimensoes;

    public Produto(String id, Dimensao dimensoes) {
        this.id = Optional.ofNullable(id)
                .filter(i -> i != null && !id.isBlank())
                .orElseThrow(() -> new IllegalArgumentException("Id nao pode ser nulo"));
        this.dimensoes = Optional.ofNullable(dimensoes)
                .orElseThrow(() -> new IllegalArgumentException("Dimensao nao pode ser nula"));
    }

    public boolean cabeNaCaixa(Caixa caixa) {
        return this.getDimensoes().getAltura() <= caixa.getDimensoes().getAltura() &&
                this.getDimensoes().getLargura() <= caixa.getDimensoes().getLargura() &&
                this.getDimensoes().getComprimento() <= caixa.getDimensoes().getComprimento();
    }
}

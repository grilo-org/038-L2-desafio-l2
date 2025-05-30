package br.com.desafio.demo.domain.vo;

import java.util.Optional;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Dimensao {
	private int altura, largura, comprimento;

	public Dimensao(int altura, int largura, int comprimento) {
		this.altura = Optional.of(altura)
				.filter(a -> a >= 0)
				.orElseThrow(() -> new IllegalArgumentException("Altura não pode ser negativa"));
		this.largura = Optional.of(largura)
				.filter(l -> l >= 0)
				.orElseThrow(() -> new IllegalArgumentException("Largura não pode ser negativa"));
		this.comprimento = Optional.of(comprimento)
				.filter(c -> c >= 0)
				.orElseThrow(() -> new IllegalArgumentException("Comprimento não pode ser negativo"));
	}

	public int getVolume() {
		return this.altura * this.largura * this.comprimento;
	}
}

package br.com.desafio.demo.domain;

import java.util.ArrayList;
import java.util.List;

import br.com.desafio.demo.domain.vo.Dimensao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Caixa {
    private String id;
    private Dimensao dimensoes;
    private String observacao;
    private List<Produto> produtos = new ArrayList<>();

    public Caixa(String caixa_id, Dimensao dimensoes, String observaca) {
        this.id = caixa_id;
        this.dimensoes = dimensoes;
        this.observacao = observaca;
    }

    public Caixa(String caixa_id, Dimensao dimensoes) {
        this.id = caixa_id;
        this.dimensoes = dimensoes;
    }

    public int getVolumeOcupado() {
        return produtos.stream().mapToInt(produto -> produto.getDimensoes().getVolume()).sum();
    }

    public boolean podeAdicionar(Produto produto) {
        int volumeOcupado = this.produtos.stream()
                .mapToInt(p -> p.getDimensoes().getVolume())
                .sum();
        int volumeDisponivel = this.dimensoes.getVolume() - volumeOcupado;
        return produto.getDimensoes().getVolume() <= volumeDisponivel && produto.cabeNaCaixa(this);
    }

    public void adicionarProduto(Produto produto) {
        this.produtos.add(produto);
    }
}

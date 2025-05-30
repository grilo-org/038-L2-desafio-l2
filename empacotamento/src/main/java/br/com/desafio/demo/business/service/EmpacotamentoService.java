package br.com.desafio.demo.business.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.desafio.demo.api.dto.input.PedidoEntrada;
import br.com.desafio.demo.api.dto.output.CaixaSaida;
import br.com.desafio.demo.api.dto.output.PedidoSaida;
import br.com.desafio.demo.api.dto.output.PedidosEmpacotados;
import br.com.desafio.demo.api.mapper.CaixaMapper;
import br.com.desafio.demo.api.mapper.ProdutoMapper;
import br.com.desafio.demo.domain.Caixa;
import br.com.desafio.demo.domain.Produto;
import br.com.desafio.demo.domain.vo.Dimensao;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpacotamentoService {
	private static final List<Caixa> TIPOS_DE_CAIXA = List.of(
			new Caixa("Caixa 1", new Dimensao(30, 40, 80)),
			new Caixa("Caixa 2", new Dimensao(80, 50, 40)),
			new Caixa("Caixa 3", new Dimensao(50, 80, 60)));
	private static final String PRODUTO_NAO_CABE_MESSAGE = "Produto não cabe em nenhuma caixa disponível.";

	private final CaixaMapper caixaMapper;
	private final ProdutoMapper produtoMapper;

	public PedidosEmpacotados empacotar(List<PedidoEntrada> pedidos) {
		List<PedidoSaida> empacotamentoFinal = pedidos.stream().map(pedido -> {
			List<Caixa> caixasUsadas = new ArrayList<>();

			// Ordena produtos do maior para o menor volume
			List<Produto> produtosOrdenados = pedido.produtos().stream()
					.map(this.produtoMapper::toProduto)
					.sorted(Comparator.comparingInt((Produto p) -> p.getDimensoes().getVolume()).reversed())
					.toList();

			for (Produto produto : produtosOrdenados) {
				// Tenta encaixar o produto em alguma caixa já aberta
				boolean encaixado = false;
				for (Caixa caixa : caixasUsadas) {
					if (caixa.podeAdicionar(produto)) {
						caixa.adicionarProduto(produto);
						encaixado = true;
						break;
					}
				}
				if (!encaixado) {
					// Abre a menor caixa possível que caiba o produto
					Caixa tipoCaixa = TIPOS_DE_CAIXA.stream()
							.sorted(Comparator.comparingInt(c -> c.getDimensoes().getVolume()))
							.filter(tipo -> produto.cabeNaCaixa(tipo))
							.findFirst()
							.orElseGet(() -> new Caixa(null, null, PRODUTO_NAO_CABE_MESSAGE));
					Caixa novaCaixa = new Caixa(tipoCaixa.getId(), tipoCaixa.getDimensoes(), tipoCaixa.getObservacao());
					novaCaixa.adicionarProduto(produto);
					caixasUsadas.add(novaCaixa);
				}
			}

			// Após empacotar todos os produtos, tenta otimizar:
			// para cada caixa, tenta mover produtos para caixas menores se possível
			caixasUsadas = otimizarCaixas(caixasUsadas);

			List<CaixaSaida> caixas = caixasUsadas.stream()
					.map(this.caixaMapper::toCaixaSaida)
					.collect(Collectors.toList());
			return new PedidoSaida(pedido.pedidoId(), caixas);
		}).toList();

		return new PedidosEmpacotados(empacotamentoFinal);
	}

	/**
	 * Tenta reempacotar os produtos das caixas abertas para minimizar o número de
	 * caixas,
	 * sempre tentando usar as menores caixas possíveis.
	 */
	private List<Caixa> otimizarCaixas(List<Caixa> caixasUsadas) {
		List<Produto> todosProdutos = caixasUsadas.stream()
				.flatMap(caixa -> caixa.getProdutos().stream())
				.sorted(Comparator.comparingInt((Produto p) -> p.getDimensoes().getVolume()).reversed())
				.toList();

		List<Caixa> caixasOtimizadas = new ArrayList<>();
		for (Produto produto : todosProdutos) {
			boolean encaixado = false;
			for (Caixa caixa : caixasOtimizadas) {
				if (caixa.podeAdicionar(produto)) {
					caixa.adicionarProduto(produto);
					encaixado = true;
					break;
				}
			}
			if (!encaixado) {
				Caixa tipoCaixa = TIPOS_DE_CAIXA.stream()
						.sorted(Comparator.comparingInt(c -> c.getDimensoes().getVolume()))
						.filter(tipo -> produto.cabeNaCaixa(tipo))
						.findFirst()
						.orElseGet(() -> new Caixa(null, null, PRODUTO_NAO_CABE_MESSAGE));
				Caixa novaCaixa = new Caixa(tipoCaixa.getId(), tipoCaixa.getDimensoes(), tipoCaixa.getObservacao());
				novaCaixa.adicionarProduto(produto);
				caixasOtimizadas.add(novaCaixa);
			}
		}
		return caixasOtimizadas;
	}
}

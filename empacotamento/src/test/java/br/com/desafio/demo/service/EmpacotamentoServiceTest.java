package br.com.desafio.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.desafio.demo.api.dto.input.PedidoEntrada;
import br.com.desafio.demo.api.dto.input.ProdutoEntrada;
import br.com.desafio.demo.api.dto.output.CaixaSaida;
import br.com.desafio.demo.api.dto.output.PedidoSaida;
import br.com.desafio.demo.api.dto.output.PedidosEmpacotados;
import br.com.desafio.demo.api.mapper.CaixaMapper;
import br.com.desafio.demo.api.mapper.ProdutoMapper;
import br.com.desafio.demo.business.service.EmpacotamentoService;
import br.com.desafio.demo.domain.Caixa;
import br.com.desafio.demo.domain.Produto;
import br.com.desafio.demo.domain.vo.Dimensao;

class EmpacotamentoServiceTest {

    private CaixaMapper caixaMapper;
    private ProdutoMapper produtoMapper;
    private EmpacotamentoService service;

    @BeforeEach
    void setup() {
        caixaMapper = mock(CaixaMapper.class);
        produtoMapper = mock(ProdutoMapper.class);
        service = new EmpacotamentoService(caixaMapper, produtoMapper);

        when(produtoMapper.toProduto(any(ProdutoEntrada.class)))
                .thenAnswer(invocation -> {
                    ProdutoEntrada entrada = invocation.getArgument(0);
                    return new Produto(entrada.produtoId(), entrada.dimensoes());
                });

        when(caixaMapper.toCaixaSaida(any(Caixa.class)))
                .thenAnswer(invocation -> {
                    Caixa caixa = invocation.getArgument(0);
                    List<String> produtos = caixa.getProdutos().stream().map(Produto::getId).toList();
                    return new CaixaSaida(caixa.getId(), produtos, caixa.getObservacao());
                });
    }

    @Test
    void givenSingleProduct_whenEmpacotar_thenReturnsOneCaixaWithProduct() {
        // given
        ProdutoEntrada produto = new ProdutoEntrada("1", new Dimensao(10, 10, 10));
        PedidoEntrada pedido = new PedidoEntrada("1", List.of(produto));

        // when
        PedidosEmpacotados resultado = service.empacotar(List.of(pedido));

        // then
        assertEquals(1, resultado.pedidos().size());
        PedidoSaida saida = resultado.pedidos().get(0);
        assertEquals("1", saida.pedidoId());
        assertEquals(1, saida.caixas().size());
        assertTrue(saida.caixas().get(0).produtos().contains("1"));
    }

    @Test
    void givenTwoLargeProducts_whenEmpacotar_thenEachInSeparateCaixa() {
        // given
        ProdutoEntrada p1 = new ProdutoEntrada("A", new Dimensao(30, 40, 80));
        ProdutoEntrada p2 = new ProdutoEntrada("B", new Dimensao(30, 40, 80));
        PedidoEntrada pedido = new PedidoEntrada("2", List.of(p1, p2));

        // when
        PedidosEmpacotados resultado = service.empacotar(List.of(pedido));

        // then
        PedidoSaida saida = resultado.pedidos().get(0);
        assertEquals(2, saida.caixas().size());
        assertTrue(saida.caixas().stream().anyMatch(c -> c.produtos().contains("A")));
        assertTrue(saida.caixas().stream().anyMatch(c -> c.produtos().contains("B")));
    }

    @Test
    void givenSmallProducts_whenEmpacotar_thenAllInOneCaixa() {
        // given
        ProdutoEntrada p1 = new ProdutoEntrada("1", new Dimensao(10, 10, 10));
        ProdutoEntrada p2 = new ProdutoEntrada("2", new Dimensao(10, 10, 10));
        ProdutoEntrada p3 = new ProdutoEntrada("3", new Dimensao(10, 10, 10));
        PedidoEntrada pedido = new PedidoEntrada("3", List.of(p1, p2, p3));

        // when
        PedidosEmpacotados resultado = service.empacotar(List.of(pedido));

        // then
        PedidoSaida saida = resultado.pedidos().get(0);
        assertEquals(1, saida.caixas().size());
        assertEquals(3, saida.caixas().get(0).produtos().size());
    }

    @Test
    void givenMultiplePedidos_whenEmpacotar_thenEachPedidoProcessedSeparately() {
        // given
        ProdutoEntrada p1 = new ProdutoEntrada("1", new Dimensao(10, 10, 10));
        ProdutoEntrada p2 = new ProdutoEntrada("2", new Dimensao(80, 50, 40));
        PedidoEntrada pedido1 = new PedidoEntrada("4", List.of(p1));
        PedidoEntrada pedido2 = new PedidoEntrada("5", List.of(p2));

        // when
        PedidosEmpacotados resultado = service.empacotar(List.of(pedido1, pedido2));

        // then
        assertEquals(2, resultado.pedidos().size());
        assertEquals("4", resultado.pedidos().get(0).pedidoId());
        assertEquals("5", resultado.pedidos().get(1).pedidoId());
    }

    @Test
    void givenNoPedidos_whenEmpacotar_thenReturnsEmptyList() {
        // given
        // when
        PedidosEmpacotados resultado = service.empacotar(List.of());

        // then
        assertTrue(resultado.pedidos().isEmpty());
    }

    @Test
    void givenProductThatDoesNotFit_whenEmpacotar_thenCaixaWithNullIdAndObservacao() {
        // given
        ProdutoEntrada produto = new ProdutoEntrada("Cadeira Gamer", new Dimensao(120, 60, 70));
        PedidoEntrada pedido = new PedidoEntrada("5", List.of(produto));

        // when
        PedidosEmpacotados resultado = service.empacotar(List.of(pedido));

        // then
        PedidoSaida saida = resultado.pedidos().get(0);
        assertEquals(1, saida.caixas().size());
        CaixaSaida caixa = saida.caixas().get(0);
        assertNull(caixa.caixaId());
        assertEquals("Produto não cabe em nenhuma caixa disponível.", caixa.observacao());
        assertTrue(caixa.produtos().contains("Cadeira Gamer"));
    }
}
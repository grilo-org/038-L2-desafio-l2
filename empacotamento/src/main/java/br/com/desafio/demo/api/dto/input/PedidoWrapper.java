package br.com.desafio.demo.api.dto.input;

import java.util.List;

public record PedidoWrapper(List<PedidoEntrada> pedidos) {
}

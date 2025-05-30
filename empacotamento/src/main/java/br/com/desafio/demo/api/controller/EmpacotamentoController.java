package br.com.desafio.demo.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.desafio.demo.api.dto.input.PedidoEntrada;
import br.com.desafio.demo.api.dto.input.PedidoWrapper;
import br.com.desafio.demo.api.dto.output.PedidosEmpacotados;
import br.com.desafio.demo.business.service.EmpacotamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Empacotamento", description = "Operações de empacotamento de pedidos")
public class EmpacotamentoController {
    private final EmpacotamentoService empacotamentoService;

    @Operation(summary = "Empacotar pedidos", description = "Recebe uma lista de pedidos e retorna o empacotamento ideal em caixas.", responses = {
            @ApiResponse(responseCode = "200", description = "Empacotamento realizado com sucesso", content = @Content(schema = @Schema(implementation = PedidosEmpacotados.class)))
    })
    @PostMapping("/empacotamento")
    public ResponseEntity<PedidosEmpacotados> empacotamento(@RequestBody @Valid PedidoWrapper pedidosWrapper) {
        log.info("Valor do pedidosWrapper: {}", pedidosWrapper);
        List<PedidoEntrada> pedidos = pedidosWrapper.pedidos();
        return ResponseEntity.ok().body(this.empacotamentoService.empacotar(pedidos));
    }
}

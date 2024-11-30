package com.avalanches_pagamento.frameworksanddrivers.api;

import com.avalanches_pagamento.enterprisebusinessrules.entities.StatusPagamento;
import com.avalanches_pagamento.frameworksanddrivers.api.dto.WebhookParams;
import com.avalanches_pagamento.frameworksanddrivers.api.interfaces.PagamentoApiInterface;
import com.avalanches_pagamento.interfaceadapters.controllers.interfaces.PagamentoControllerInterface;
import com.avalanches_pagamento.interfaceadapters.presenters.dtos.WebHookDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/avalanches/v1/pagamento")
@Validated
public class PagamentoApi implements PagamentoApiInterface {

    private final PagamentoControllerInterface pagamentoController;

    public PagamentoApi(PagamentoControllerInterface pagamentoController) {
        this.pagamentoController = pagamentoController;
    }

    @PostMapping("/webhook")
    @Override
    public ResponseEntity<WebHookDto> webhook(@Valid @RequestBody WebhookParams webhook) {
        try {
            System.out.println("Payload recebido: idPedido=" + webhook.idPedido() + ", status=" + webhook.status());
            pagamentoController.webhook(webhook);
            return ResponseEntity.ok(new WebHookDto(true, "Webhook recebido com sucesso"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new WebHookDto(false, "Ocorreu um erro ao processar o webhook"));
        }
    }

    @GetMapping("/status/{idPedido}")
    @Override
    public ResponseEntity<StatusPagamento> consultaStatus(@PathVariable("idPedido") Integer idPedido) {
        StatusPagamento response = pagamentoController.consultaStatus(idPedido);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/efetuar-pagamento/{idPedido}")
    @Override
    public ResponseEntity<Boolean> efetuarPagamento(@PathVariable("idPedido") Integer idPedido) {
        Boolean response = pagamentoController.efetuarPagamento(idPedido);
        return ResponseEntity.ok().body(response);
    }

}

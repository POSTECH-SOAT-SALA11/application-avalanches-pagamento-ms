package com.avalanches.frameworksanddrivers.api;

import com.avalanches.enterprisebusinessrules.entities.StatusPagamento;
import com.avalanches.frameworksanddrivers.api.dto.WebHookMockParams;
import com.avalanches.frameworksanddrivers.api.dto.WebhookParams;
import com.avalanches.frameworksanddrivers.api.interfaces.PagamentoApiInterface;
import com.avalanches.frameworksanddrivers.databases.interfaces.BancoDeDadosContextoInterface;
import com.avalanches.interfaceadapters.controllers.PagamentoController;
import com.avalanches.interfaceadapters.controllers.interfaces.PagamentoControllerInterface;
import com.avalanches.interfaceadapters.presenters.dtos.WebHookDto;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/avalanches/v1/pagamento")
@Validated
public class PagamentoApi implements PagamentoApiInterface {

    @Inject
    private BancoDeDadosContextoInterface bancoDeDadosContexto;

    @Autowired
    private WebHookMockParams webHookMockParams;

    @PostMapping("/webhook")
    @Override
    public ResponseEntity<WebHookDto> webhook(@Valid @RequestBody WebhookParams webhook) {
        try {
            System.out.println("Payload recebido: idPedido=" + webhook.idPedido() + ", status=" + webhook.status());
            PagamentoController pagamentoController = createPagamentoController();
            pagamentoController.webhook(webhook, bancoDeDadosContexto, webHookMockParams);
            return ResponseEntity.ok(new WebHookDto(true, "Webhook recebido com sucesso"));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new WebHookDto(false, "Ocorreu um erro ao processar o webhook"));
        }
    }

    protected PagamentoController createPagamentoController() {
        return new PagamentoController();
    }

    @GetMapping("/status/{idPedido}")
    @Override
    public ResponseEntity<StatusPagamento> consultaStatus(@PathVariable("idPedido") Integer idPedido) {
        PagamentoControllerInterface pagamentoController = new PagamentoController();
        StatusPagamento response = pagamentoController.consultaStatus(idPedido, bancoDeDadosContexto, webHookMockParams);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/efetuar-pagamento/{idPedido}")
    @Override
    public ResponseEntity<Boolean> efetuarPagamento(@PathVariable("idPedido") Integer idPedido) {
        PagamentoControllerInterface pagamentoController = new PagamentoController();
        Boolean response = pagamentoController.efetuarPagamento(idPedido, bancoDeDadosContexto, webHookMockParams);
        return ResponseEntity.ok().body(response);
    }

}

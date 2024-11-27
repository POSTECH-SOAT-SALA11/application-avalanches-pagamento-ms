package com.avalanches.interfaceadapters.controllers;

import com.avalanches.applicationbusinessrules.usecases.PagamentoUseCase;
import com.avalanches.enterprisebusinessrules.entities.StatusPagamento;
import com.avalanches.frameworksanddrivers.api.dto.WebHookMockParams;
import com.avalanches.frameworksanddrivers.api.dto.WebhookParams;
import com.avalanches.frameworksanddrivers.databases.interfaces.BancoDeDadosContextoInterface;
import com.avalanches.interfaceadapters.controllers.interfaces.PagamentoControllerInterface;
import com.avalanches.interfaceadapters.gateways.PagamentoGateway;
import com.avalanches.interfaceadapters.gateways.interfaces.PagamentoGatewayInterface;

public class PagamentoController implements PagamentoControllerInterface {

    @Override
    public void webhook(WebhookParams webhook, BancoDeDadosContextoInterface bancoDeDadosContexto, WebHookMockParams webHookMockParams) {
        PagamentoGatewayInterface pagamentoGateway = createPagamentoGateway(bancoDeDadosContexto, webHookMockParams);
        PagamentoUseCase pagamentoUseCase = createPagamentoUseCase(); // Call the method that creates PagamentoUseCase
        pagamentoUseCase.cadastrar(webhook, pagamentoGateway);
    }

    @Override
    public StatusPagamento consultaStatus(Integer idPedido, BancoDeDadosContextoInterface bancoDeDadosContexto, WebHookMockParams webHookMockParams) {
        PagamentoGatewayInterface pagamentoGateway = createPagamentoGateway(bancoDeDadosContexto, webHookMockParams);
        PagamentoUseCase pagamentoUseCase = createPagamentoUseCase();
        return pagamentoUseCase.consultaStatus(idPedido, pagamentoGateway);
    }

    protected PagamentoGateway createPagamentoGateway(
            BancoDeDadosContextoInterface bancoDeDadosContexto,
            WebHookMockParams webHookMockParams
    ) {
        return new PagamentoGateway(bancoDeDadosContexto, webHookMockParams);
    }

    protected PagamentoUseCase createPagamentoUseCase() {
        return new PagamentoUseCase();
    }

}

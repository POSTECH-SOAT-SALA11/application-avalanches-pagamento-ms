package com.avalanches_pagamento.interfaceadapters.controllers;

import com.avalanches_pagamento.applicationbusinessrules.usecases.interfaces.PagamentoUseCaseInterface;
import com.avalanches_pagamento.enterprisebusinessrules.entities.StatusPagamento;
import com.avalanches_pagamento.frameworksanddrivers.api.dto.WebhookParams;
import com.avalanches_pagamento.interfaceadapters.controllers.interfaces.PagamentoControllerInterface;
import com.avalanches_pagamento.interfaceadapters.gateways.interfaces.PagamentoGatewayInterface;
import org.springframework.stereotype.Component;

@Component
public class PagamentoController implements PagamentoControllerInterface {

    private final PagamentoGatewayInterface pagamentoGateway;

    private final PagamentoUseCaseInterface pagamentoUseCase;

    public PagamentoController(PagamentoGatewayInterface pagamentoGateway,
                               PagamentoUseCaseInterface pagamentoUseCase) {
        this.pagamentoGateway = pagamentoGateway;
        this.pagamentoUseCase = pagamentoUseCase;
    }

    @Override
    public void webhook(WebhookParams webhook) {
        pagamentoUseCase.cadastrar(webhook, pagamentoGateway);
    }

    @Override
    public StatusPagamento consultaStatus(Integer idPedido) {
        return pagamentoUseCase.consultaStatus(idPedido, pagamentoGateway);
    }

    @Override
    public Boolean efetuarPagamento(Integer idPedido) {
        return pagamentoUseCase.efetuarPagamento(idPedido, pagamentoGateway);
    }

}

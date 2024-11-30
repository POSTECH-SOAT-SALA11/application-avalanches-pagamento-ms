package com.avalanches_pagamento.applicationbusinessrules.usecases;

import com.avalanches_pagamento.applicationbusinessrules.usecases.interfaces.PagamentoUseCaseInterface;
import com.avalanches_pagamento.enterprisebusinessrules.entities.Pagamento;
import com.avalanches_pagamento.enterprisebusinessrules.entities.StatusPagamento;
import com.avalanches_pagamento.frameworksanddrivers.api.dto.WebhookParams;
import com.avalanches_pagamento.interfaceadapters.gateways.interfaces.PagamentoGatewayInterface;
import org.springframework.stereotype.Component;
import org.webjars.NotFoundException;

@Component
public class PagamentoUseCase implements PagamentoUseCaseInterface {

    @Override
    public void cadastrar(WebhookParams webhook, PagamentoGatewayInterface pagamentoGateway){
        Pagamento pagamento = montarPagamento(webhook);
        pagamentoGateway.cadastrar(pagamento);
    }

    @Override
    public Boolean efetuarPagamento(Integer idPedido, PagamentoGatewayInterface pagamentoGateway) {
        return pagamentoGateway.efetuarPagamento(idPedido);
    }

    @Override
    public StatusPagamento consultaStatus(Integer idPedido, PagamentoGatewayInterface pagamentoGateway) {
        if (!pagamentoGateway.verificaPagamentoExiste(idPedido))  {
            throw new NotFoundException("Pagamento não encontrado.");
        }
        return pagamentoGateway.consultaStatus(idPedido);
    }

    private static Pagamento montarPagamento(WebhookParams webhook) {
        return new Pagamento(webhook.idPedido(), webhook.status());
    }

}

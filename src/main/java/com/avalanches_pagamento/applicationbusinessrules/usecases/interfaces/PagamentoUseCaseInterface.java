package com.avalanches_pagamento.applicationbusinessrules.usecases.interfaces;

import com.avalanches_pagamento.enterprisebusinessrules.entities.StatusPagamento;
import com.avalanches_pagamento.frameworksanddrivers.api.dto.WebhookParams;
import com.avalanches_pagamento.interfaceadapters.gateways.interfaces.PagamentoGatewayInterface;

public interface PagamentoUseCaseInterface {

    void cadastrar(WebhookParams webhook, PagamentoGatewayInterface pagamentoGateway);

    Boolean efetuarPagamento(Integer idPedido, PagamentoGatewayInterface pagamentoGateway);

    StatusPagamento consultaStatus(Integer idPedido, PagamentoGatewayInterface pagamentoGateway);

}

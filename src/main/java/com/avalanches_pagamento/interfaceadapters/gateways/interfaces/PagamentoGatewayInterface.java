package com.avalanches_pagamento.interfaceadapters.gateways.interfaces;

import com.avalanches_pagamento.enterprisebusinessrules.entities.Pagamento;
import com.avalanches_pagamento.enterprisebusinessrules.entities.StatusPagamento;

public interface PagamentoGatewayInterface {

    void cadastrar(Pagamento pagamento);

    Boolean efetuarPagamento(Integer idPedido);

    boolean verificaPagamentoExiste(Integer idPedido);

    StatusPagamento consultaStatus(Integer idPedido);
}

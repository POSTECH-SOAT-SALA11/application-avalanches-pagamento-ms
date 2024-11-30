package com.avalanches_pagamento.interfaceadapters.controllers.interfaces;

import com.avalanches_pagamento.enterprisebusinessrules.entities.StatusPagamento;
import com.avalanches_pagamento.frameworksanddrivers.api.dto.WebHookMockParams;
import com.avalanches_pagamento.frameworksanddrivers.api.dto.WebhookParams;
import com.avalanches_pagamento.frameworksanddrivers.databases.interfaces.BancoDeDadosContextoInterface;

public interface PagamentoControllerInterface {

    void webhook(WebhookParams webhook);

    StatusPagamento consultaStatus(Integer idPedido);

    Boolean efetuarPagamento(Integer idPedido);

}

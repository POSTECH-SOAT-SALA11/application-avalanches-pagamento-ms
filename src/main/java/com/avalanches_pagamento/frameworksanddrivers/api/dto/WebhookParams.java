package com.avalanches_pagamento.frameworksanddrivers.api.dto;

import com.avalanches_pagamento.enterprisebusinessrules.entities.StatusPagamento;
import jakarta.validation.constraints.NotNull;

public record WebhookParams(
        @NotNull(message = "id do pedido não pode ser nulo") Integer idPedido,
        @NotNull(message = "status não pode ser nulo") StatusPagamento status
) {
}

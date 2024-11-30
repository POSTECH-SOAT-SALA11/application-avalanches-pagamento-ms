package com.avalanches.enterprisebusinessrules.entities;

public class Pagamento {

    private Integer idPedido;
    private StatusPagamento statusPagamento;

    public Pagamento(Integer idPedido, StatusPagamento statusPagamento) {
        this.idPedido = idPedido;
        this.statusPagamento = statusPagamento;
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public StatusPagamento getStatusPagamento() {
        return statusPagamento;
    }
}

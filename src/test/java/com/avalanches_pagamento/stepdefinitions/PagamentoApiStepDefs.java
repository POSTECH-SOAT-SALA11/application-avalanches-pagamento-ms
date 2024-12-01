package com.avalanches_pagamento.stepdefinitions;

import com.avalanches_pagamento.applicationbusinessrules.usecases.PagamentoUseCase;
import com.avalanches_pagamento.enterprisebusinessrules.entities.Pagamento;
import com.avalanches_pagamento.enterprisebusinessrules.entities.StatusPagamento;
import com.avalanches_pagamento.frameworksanddrivers.api.PagamentoApi;
import com.avalanches_pagamento.frameworksanddrivers.api.dto.WebHookMockParams;
import com.avalanches_pagamento.frameworksanddrivers.api.dto.WebhookParams;
import com.avalanches_pagamento.frameworksanddrivers.api.interfaces.PagamentoApiInterface;
import com.avalanches_pagamento.frameworksanddrivers.databases.interfaces.BancoDeDadosContextoInterface;
import com.avalanches_pagamento.interfaceadapters.gateways.PagamentoGateway;
import com.avalanches_pagamento.interfaceadapters.controllers.PagamentoController;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PagamentoApiStepDefs {

    @Autowired
    private WebHookMockParams mockWebHookMockParams;

    @Autowired
    private BancoDeDadosContextoInterface mockBancoDeDadosContexto;

    private PagamentoGateway pagamentoGateway;
    private PagamentoUseCase pagamentoUseCase;
    private PagamentoController pagamentoController;
    private PagamentoApiInterface pagamentoApi;
    private WebhookParams webhookParams;
    private ResponseEntity<Boolean> response;

    @Given("uma requisição com idPedido {string} e status {string} é feita")
    public void givenPaymentRequest(String idPedido, String status) {
        StatusPagamento statusEnum = StatusPagamento.valueOf(status.toUpperCase());
        webhookParams = new WebhookParams(Integer.parseInt(idPedido), statusEnum);

        pagamentoGateway = spy(new PagamentoGateway(mockBancoDeDadosContexto, mockWebHookMockParams));
        pagamentoUseCase = spy(new PagamentoUseCase());
        pagamentoController = spy(new PagamentoController(pagamentoGateway, pagamentoUseCase));
        pagamentoApi = spy(new PagamentoApi(pagamentoController));
    }


    @When("a requisição é chamada para efetuar o pagamento com o idPedido {string}")
    public void whenApiCalledEfetuarPagamento(String idPedido) {
        when(pagamentoController.efetuarPagamento(Integer.parseInt(idPedido))).thenReturn(true);

        pagamentoApi.webhook(webhookParams);

        response = pagamentoApi.efetuarPagamento(Integer.parseInt(idPedido));
    }

    @Then("o pagamento vai ser processado com sucesso")
    public void thenPaymentProcessedSuccessfully() {
        assertEquals(ResponseEntity.ok().body(true), response);
    }

    @And("o status do pagamento deve ser atualizado para {string} no sistema")
    public void thenStatusUpdatedInSystem(String expectedStatusString) {
        StatusPagamento expectedStatus = StatusPagamento.valueOf(expectedStatusString.toUpperCase());

        ArgumentCaptor<Pagamento> pagamentoCaptor = ArgumentCaptor.forClass(Pagamento.class);

        verify(pagamentoGateway, times(1)).cadastrar(pagamentoCaptor.capture());

        Pagamento capturedPagamento = pagamentoCaptor.getValue();

        assertEquals(expectedStatus, capturedPagamento.getStatusPagamento());
    }

    @And("o webhook externo tem que ser chamado")
    public void thenWebhookCalled() {
        verify(pagamentoGateway, times(1)).efetuarPagamento(anyInt());
    }
}
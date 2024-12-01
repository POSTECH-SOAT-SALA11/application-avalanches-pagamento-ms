Feature: Test API and Gateway Interaction for Pagamento

  Scenario: Efetuar pagamento ativa o webhook externo e atualiza o status do pagamento
    Given uma requisição com idPedido "123" e status "Aprovado" é feita
    When a requisição é chamada para efetuar o pagamento com o idPedido "123"
    Then o pagamento vai ser processado com sucesso
    And o status do pagamento deve ser atualizado para "Aprovado" no sistema
    And o webhook externo tem que ser chamado
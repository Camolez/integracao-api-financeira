
# Projeto 

Imagine que o nosso sistema é legado e que precisamos atualizá-lo! Pensamos em
utilizar microsserviços, desenvolvendo soluções com as melhores práticas do
mercado.

- 1 -  Método para solicitar pagamentos;

- 2 - Método para consultar todos os pagamentos e
pagamentos associados. Ex: por Pedido, data, Usuário etc;

- 3 -Método para cancelar pagamentos, precisamos que este
método de cancelamento seja flexível, pois nossos clientes utilizam ele de
formas diferentes, ou seja, precisamos que seja possível cancelar a
transação por: HEADER, PARAM e PATH.

## Tecnologias
 - Spring Boot
 - Spring Data Jpa
 - Spring Mvc
 - h2 (DB -> create-drop)
 - JUnit
 - WireMoc
 - Feign
 
## Endpoints


**URL** -  http://localhost:8080/integracaoAdiq/findAll
 - Lista todos os pagamentos realizados
##### Metodo `GET`
##### Retorno sucesso:**`200`
 
**Exemplo requisição:**
  ```
[
  {
    "amount": 0,
    "authorizationCode": "string",
    "captureType": "string",
    "city": "string",
    "country": "string",
    "currencyCode": "string",
    "datePayment": "2020-10-18T20:45:34.565Z",
    "documentNumber": "string",
    "firstName": "string",
    "id": 0,
    "lastName": "string",
    "orderNumber": "string",
    "paymentId": "string",
    "productType": "string",
    "state": "string",
    "statusPayment": "string",
    "transactionType": "string"
  }
]
```

**URL** -  http://localhost:8080/integracaoAdiq/requestPayment
 - Solicitação de pagamento
##### Metodo `POST`
##### Retorno sucesso:`200`
##### Retorno erro:`400` - _Validar informações enviadas_
   ```
  {
  "mensagem": "FAVOR INSERIR UM TOKEN VALIDO"
  }
  {
    "field": "payment.currencyCode",
    "message": "FAVOR INFORMAR UMA MODEDA VALIDA: BRL"
  },
  {
    "field": "cardInfo.brand",
    "message": "O campo brand deve conter visa, mastercard, amex ou elo"
  },
  {
    "field": "payment.productType",
    "message": "FAVOR INFORMAR UMA VALOR VALIDO: avista, debito, lojista OU emissor"
  },
  {
    "field": "payment.captureType",
    "message": "FAVOR INFORMAR UMA VALOR VALIDO: ac OU pa"
  },
  {
    "field": "payment.transactionType",
    "message": "FAVOR INFORMAR UM VALOR VALIDO: CREDIT OU DEBIT"
  }
```

**URL** -  http://localhost:8080/integracaoAdiq/cancelPayment/{idPath} Ou http://localhost:8080/integracaoAdiq/cancelPayment

 
 - Cancelamento lógico do pagamento
##### Metodo `PATH`
##### Retorno sucesso:`200`
##### Retorno erro:`405` - _Pagamento não existente_
   ```
  {
  "mensagem": "ID PAGAMENTO NÃO ENCONTRADO"
 }
  
```

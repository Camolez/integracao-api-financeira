
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

package com.br.finnet.integracaoAdiq.service;

import com.br.finnet.integracaoAdiq.domain.entities.PaymentEntity;
import com.br.finnet.integracaoAdiq.domain.models.request.PaymentRequestModel;
import com.br.finnet.integracaoAdiq.domain.models.request.TokenRequestModel;
import com.br.finnet.integracaoAdiq.domain.models.response.AdiqGetTokenResponse;
import com.br.finnet.integracaoAdiq.domain.repositories.PaymentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ServiceApiAdiqTeste {

    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    private static final String HEADER_APPLICATION_JSON = "application/json";
    private static final String BODY_BAD_REQUEST = "{\"payment\":{\"transactionType\":\"debit\",\"amount\":0,\"currencyCode\":\"string\",\"productType\":\"string\",\"installments\":0,\"captureType\":\"string\",\"recurrent\":true},\"cardInfo\":{\"vaultId\":\"string\",\"numberToken\":\"string\",\"cardholderName\":\"string\",\"securityCode\":\"string\",\"brand\":\"mastercard\",\"expirationMonth\":\"string\",\"expirationYear\":\"string\"},\"sellerInfo\":{\"orderNumber\":\"string\",\"softDescriptor\":\"string\",\"dynamicMcc\":0,\"code3DS\":\"string\",\"urlSite3DS\":\"string\"},\"customer\":{\"documentType\":\"string\",\"documentNumber\":\"string\",\"firstName\":\"string\",\"lastName\":\"string\",\"email\":\"string\",\"phoneNumber\":\"string\",\"mobilePhoneNumber\":\"string\",\"address\":\"string\",\"complement\":\"string\",\"city\":\"string\",\"state\":\"string\",\"zipCode\":\"string\",\"ipAddress\":\"string\",\"country\":\"string\"},\"deviceInfo\":{\"httpAcceptBrowserValue\":\"string\",\"httpAcceptContent\":\"string\",\"httpBrowserLanguage\":\"string\",\"httpBrowserJavaEnabled\":\"string\",\"httpBrowserJavaScriptEnabled\":\"string\",\"httpBrowserColorDepth\":\"string\",\"httpBrowserScreenHeight\":\"string\",\"httpBrowserScreenWidth\":\"string\",\"httpBrowserTimeDifference\":\"string\",\"userAgentBrowserValue\":\"string\"},\"sellers\":[{\"id\":\"string\",\"amount\":\"string\",\"items\":[{\"id\":\"string\",\"amount\":0,\"description\":\"string\",\"ratePercent\":0.0,\"rateAmount\":0}]}],\"grantType\":\"123\",\"authorization\":\"token\"}";
    private static final String BODY_REQUEST_SUCESSO = "{\"payment\":{\"transactionType\":\"debit\",\"amount\":0,\"currencyCode\":\"BRL\",\"productType\":\"avista\",\"installments\":0,\"captureType\":\"ac\",\"recurrent\":true},\"cardInfo\":{\"vaultId\":\"string\",\"numberToken\":\"string\",\"cardholderName\":\"string\",\"securityCode\":\"string\",\"brand\":\"mastercard\",\"expirationMonth\":\"string\",\"expirationYear\":\"string\"},\"sellerInfo\":{\"orderNumber\":\"string\",\"softDescriptor\":\"string\",\"dynamicMcc\":0,\"code3DS\":\"string\",\"urlSite3DS\":\"string\"},\"customer\":{\"documentType\":\"string\",\"documentNumber\":\"string\",\"firstName\":\"string\",\"lastName\":\"string\",\"email\":\"string\",\"phoneNumber\":\"string\",\"mobilePhoneNumber\":\"string\",\"address\":\"string\",\"complement\":\"string\",\"city\":\"string\",\"state\":\"string\",\"zipCode\":\"string\",\"ipAddress\":\"string\",\"country\":\"string\"},\"deviceInfo\":{\"httpAcceptBrowserValue\":\"string\",\"httpAcceptContent\":\"string\",\"httpBrowserLanguage\":\"string\",\"httpBrowserJavaEnabled\":\"string\",\"httpBrowserJavaScriptEnabled\":\"string\",\"httpBrowserColorDepth\":\"string\",\"httpBrowserScreenHeight\":\"string\",\"httpBrowserScreenWidth\":\"string\",\"httpBrowserTimeDifference\":\"string\",\"userAgentBrowserValue\":\"string\"},\"sellers\":[{\"id\":\"string\",\"amount\":\"string\",\"items\":[{\"id\":\"string\",\"amount\":0,\"description\":\"string\",\"ratePercent\":0.0,\"rateAmount\":0}]}],\"grantType\":\"123\",\"authorization\":\"token\"}";
    private static final String ACCESS_TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6WyJBRElRIiwiQURJUQ";
    private static final String RESPONSE_SUCESSO_PAGAMENTO = "{\"paymentAuthorization\":{\"returnCode\":\"0\",\"description\":\"Sucesso\",\"paymentId\":\"020005829307302178050000039964150000000000\",\"authorizationCode\":\"012345\",\"orderNumber\":\"2020098011\",\"expireAt\":null,\"amount\":1115,\"releaseAt\":\"2020-07-30T00:00:00+00:00\"}}";
    WireMockServer wireMockServer = new WireMockServer(8082);

    @Autowired
    PaymentRepository paymentRepository;

    @LocalServerPort
    private int port;

    @Test
    public void testeSucessoPagamentoAdiq() throws JsonProcessingException {
        wireMockServer.start();
        wireMockServer.stubFor(WireMock.post(WireMock.urlEqualTo("/auth/oauth2/v1/token"))
                .withHeader(HEADER_CONTENT_TYPE, WireMock.matching(HEADER_APPLICATION_JSON))
                .withHeader("Authorization", WireMock.matching("token"))
                .withRequestBody(WireMock.equalToJson(new ObjectMapper().writeValueAsString(TokenRequestModel.builder()
                        .grantType("123").build())))
                .willReturn(WireMock
                        .aResponse()
                        .withStatus(200)
                        .withHeader(HEADER_CONTENT_TYPE, HEADER_APPLICATION_JSON)
                        .withBody(new ObjectMapper().writeValueAsString(AdiqGetTokenResponse.builder()
                                .accessToken(ACCESS_TOKEN)
                                .tokenType("Bearer")
                                .expiresIn(10000)
                                .scope("GatewayEcommerce")
                                .build()))
                )
        );

                wireMockServer.stubFor(WireMock.post(WireMock.urlEqualTo("/v1/payments"))
                .withHeader(HEADER_CONTENT_TYPE, WireMock.matching(HEADER_APPLICATION_JSON))
                .withHeader("Authorization", WireMock.matching("Bearer "+ACCESS_TOKEN))
                .withRequestBody(WireMock.equalToJson(BODY_REQUEST_SUCESSO))
                .willReturn(WireMock
                        .aResponse()
                        .withStatus(200)
                        .withHeader(HEADER_CONTENT_TYPE, HEADER_APPLICATION_JSON)
                        .withBody(RESPONSE_SUCESSO_PAGAMENTO)
                )
        );
            RestAssured.given()
                    .baseUri("http://localhost:"+port)
                    .when()
                    .header(HEADER_CONTENT_TYPE, HEADER_APPLICATION_JSON)
                    .body(BODY_REQUEST_SUCESSO)
                    .post("/integracaoAdiq/requestPayment")
                    .then()
                    .statusCode(201);
        wireMockServer.stop();

    }

    @Test
    public void testeBadRequestPagamentoAdiq() throws JsonProcessingException {
        wireMockServer.start();
        wireMockServer.stubFor(WireMock.post(WireMock.urlEqualTo("/auth/oauth2/v1/token"))
                .withHeader(HEADER_CONTENT_TYPE, WireMock.matching(HEADER_APPLICATION_JSON))
                .withHeader("Authorization", WireMock.matching("token"))
                .withRequestBody(WireMock.equalToJson(new ObjectMapper().writeValueAsString(TokenRequestModel.builder()
                        .grantType("123").build())))
                .willReturn(WireMock
                        .aResponse()
                        .withStatus(200)
                        .withHeader(HEADER_CONTENT_TYPE, HEADER_APPLICATION_JSON)
                        .withBody(new ObjectMapper().writeValueAsString(AdiqGetTokenResponse.builder()
                                .accessToken(ACCESS_TOKEN)
                                .tokenType("Bearer")
                                .expiresIn(10000)
                                .scope("GatewayEcommerce")
                                .build()))
                )
        );

                wireMockServer.stubFor(WireMock.post(WireMock.urlEqualTo("/v1/payments"))
                .withHeader(HEADER_CONTENT_TYPE, WireMock.matching(HEADER_APPLICATION_JSON))
                .withHeader("Authorization", WireMock.matching("Bearer "+ACCESS_TOKEN))
                .withRequestBody(WireMock.equalToJson(BODY_BAD_REQUEST))
                .willReturn(WireMock
                        .aResponse()
                        .withStatus(400)
                        .withHeader(HEADER_CONTENT_TYPE, HEADER_APPLICATION_JSON)
                        .withBody(RESPONSE_SUCESSO_PAGAMENTO)
                )
        );
            RestAssured.given()
                    .baseUri("http://localhost:"+port)
                    .when()
                    .header(HEADER_CONTENT_TYPE, HEADER_APPLICATION_JSON)
                    .body(BODY_BAD_REQUEST)
                    .post("/integracaoAdiq/requestPayment")
                    .then()
                    .statusCode(400);
            wireMockServer.stop();

    }

    @Test
    public void testeCancelPagamentoSucessoParam() throws JsonProcessingException {
        this.testeSucessoPagamentoAdiq();
        List<PaymentEntity> entityList =paymentRepository.findAll();
        RestAssured.given()
                .baseUri("http://localhost:"+port)
                .when()
                .header(HEADER_CONTENT_TYPE, HEADER_APPLICATION_JSON)
                .patch("/integracaoAdiq/cancelPayment?id="+entityList.get(0).getId())
                .then()
                .statusCode(200);

    }

    @Test
    public void testeCancelPagamentoSucessoPath() throws JsonProcessingException {
        this.testeSucessoPagamentoAdiq();
        List<PaymentEntity> entityList =paymentRepository.findAll();
        RestAssured.given()
                .baseUri("http://localhost:"+port)
                .when()
                .header(HEADER_CONTENT_TYPE, HEADER_APPLICATION_JSON)
                .patch("/integracaoAdiq/cancelPayment/"+entityList.get(0).getId())
                .then()
                .statusCode(200);

    }
    @Test
    public void testeCancelamentoPagamentoIdNaoEncontrado() {
            RestAssured.given()
                    .baseUri("http://localhost:"+port)
                    .when()
                    .header(HEADER_CONTENT_TYPE, HEADER_APPLICATION_JSON)
                    .post("/integracaoAdiq/cancelPayment/1")
                    .then()
                    .statusCode(405);
    }
}

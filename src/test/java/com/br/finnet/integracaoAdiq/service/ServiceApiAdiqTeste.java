package com.br.finnet.integracaoAdiq.service;

import com.br.finnet.integracaoAdiq.domain.models.request.PaymentRequestModel;
import com.br.finnet.integracaoAdiq.domain.models.request.TokenRequestModel;
import com.br.finnet.integracaoAdiq.domain.models.response.AdiqGetTokenResponse;
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
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ServiceApiAdiqTeste {

    private static final String URL_BASE_HOMOLOG_ADIQ = "https://ecommerce-hml.adiq.io";
    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    private static final String HEADER_APPLICATION_JSON = "application/json";
    private static final String BODY_REQUEST_SUCESSP = "{\"payment\":{\"transactionType\":\"debit\",\"amount\":0,\"currencyCode\":\"string\",\"productType\":\"string\",\"installments\":0,\"captureType\":\"string\",\"recurrent\":true},\"cardInfo\":{\"vaultId\":\"string\",\"numberToken\":\"string\",\"cardholderName\":\"string\",\"securityCode\":\"string\",\"brand\":\"mastercard\",\"expirationMonth\":\"string\",\"expirationYear\":\"string\"},\"sellerInfo\":{\"orderNumber\":\"string\",\"softDescriptor\":\"string\",\"dynamicMcc\":0,\"code3DS\":\"string\",\"urlSite3DS\":\"string\"},\"customer\":{\"documentType\":\"string\",\"documentNumber\":\"string\",\"firstName\":\"string\",\"lastName\":\"string\",\"email\":\"string\",\"phoneNumber\":\"string\",\"mobilePhoneNumber\":\"string\",\"address\":\"string\",\"complement\":\"string\",\"city\":\"string\",\"state\":\"string\",\"zipCode\":\"string\",\"ipAddress\":\"string\",\"country\":\"string\"},\"deviceInfo\":{\"httpAcceptBrowserValue\":\"string\",\"httpAcceptContent\":\"string\",\"httpBrowserLanguage\":\"string\",\"httpBrowserJavaEnabled\":\"string\",\"httpBrowserJavaScriptEnabled\":\"string\",\"httpBrowserColorDepth\":\"string\",\"httpBrowserScreenHeight\":\"string\",\"httpBrowserScreenWidth\":\"string\",\"httpBrowserTimeDifference\":\"string\",\"userAgentBrowserValue\":\"string\"},\"sellers\":[{\"id\":\"string\",\"amount\":\"string\",\"items\":[{\"id\":\"string\",\"amount\":0,\"description\":\"string\",\"ratePercent\":0.0,\"rateAmount\":0}]}],\"grantType\":\"123\",\"authorization\":\"token\"}";
    @Autowired
    MockMvc mockMvc;

    WireMockServer wireMockServer = new WireMockServer(8082);

    @Test
    public void testeGetTokenApiAdiq() throws JsonProcessingException {

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
                                .accessToken("eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6WyJBRElRIiwiQURJUQ")
                                .tokenType("Bearer")
                                .expiresIn(10000)
                                .scope("GatewayEcommerce")
                                .build()))
                )
        );

        wireMockServer.stubFor(WireMock.post(WireMock.urlEqualTo("/v1/payments"))
                .withHeader(HEADER_CONTENT_TYPE, WireMock.matching(HEADER_APPLICATION_JSON))
                .withHeader("Authorization", WireMock.matching("Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6WyJBRElRIiwiQURJUQ"))
                .withRequestBody(WireMock.equalToJson(BODY_REQUEST_SUCESSP))
                .willReturn(WireMock
                        .aResponse()
                        .withStatus(200)
                        .withHeader(HEADER_CONTENT_TYPE, HEADER_APPLICATION_JSON)
                        .withBody("{\n" +
                                "                          \"paymentAuthorization\": {\n" +
                                "                            \"returnCode\": \"00\",\n" +
                                "                            \"description\": \"Authorized\",\n" +
                                "                            \"paymentId\": \"020080286103040952150000006201850000000000\",\n" +
                                "                            \"authorizationCode\": \"043711\",\n" +
                                "                            \"orderNumber\": \"0000000001\",\n" +
                                "                            \"expireAt\": \"2019-09-24T13:20:52.8775511-03:00\",\n" +
                                "                            \"amount\": 1035,\n" +
                                "                            \"releaseAt\": \"2019-09-24T13:20:52.877545-03:00\"\n" +
                                "                          }\n" +
                                "                        }\n" +
                                "\t                \t")
                )
        );

        RestAssured.given()
                .baseUri("http://localhost:"+port)
                .when()
                .header(HEADER_CONTENT_TYPE, HEADER_APPLICATION_JSON)
                .body(BODY_REQUEST_SUCESSP)
                .post("/integracaoAdiq/requestPayment")
                .then()
                .statusCode(201);
    }
    @LocalServerPort
    private int port;

    @Test
    public void requestPaymentFieldError() {
        PaymentRequestModel paymentRequestModel  = PaymentRequestModel.builder()
                .authorization("token")
                .grantType("123")
                .build();


        RestAssured
                .given()
                .baseUri("http://localhost:"+port)
                .when()
                .header(HEADER_CONTENT_TYPE, HEADER_APPLICATION_JSON)
                .body(paymentRequestModel)
                .post("/integracaoAdiq/requestPayment")
                .then()
                .statusCode(400);
    }



}

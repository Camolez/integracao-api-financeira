package com.br.finnet.integracaoAdiq.service;

import com.br.finnet.integracaoAdiq.domain.models.response.AdiqGetTokenModel;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.br.finnet.integracaoAdiq.service.impl.ApiAdiqImpl;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import lombok.SneakyThrows;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Response;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.web.client.match.ContentRequestMatchers;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.ServletContext;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ServiceApiAdiqTeste {

    private static final String URL_BASE_HOMOLOG_ADIQ = "https://ecommerce-hml.adiq.io";
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ApiAdiqImpl apiAdiq;


    WireMockServer wireMockServer = new WireMockServer(8080);
    /*@InjectMocks


    @BeforeTestMethod
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(apiAdiq).build();
    }*/

    @SneakyThrows
    @Test
    public void testeGetTokenApiAdiq(){
     mockMvc.perform(MockMvcRequestBuilders.post(URL_BASE_HOMOLOG_ADIQ + "/auth/oauth2/v1/token")
                        .header("Authorization", "Basic ZGV2ZWxvcGVycy5hZGlxLmlv")
                        .content("grantType=")
        ).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().is(404)).andReturn();
    }

}

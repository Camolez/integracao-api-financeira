package com.br.finnet.integracaoAdiq.service.impl;

import com.br.finnet.integracaoAdiq.domain.enums.CurrencyEnum;
import com.br.finnet.integracaoAdiq.domain.enums.TransactionTypeEnum;
import com.br.finnet.integracaoAdiq.domain.models.request.PaymentModel;
import com.br.finnet.integracaoAdiq.domain.repositories.PaymentRepository;
import com.br.finnet.integracaoAdiq.service.ApiAdiqService;
import com.sun.xml.bind.v2.TODO;
import lombok.AllArgsConstructor;
import org.apache.http.HttpVersion;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.awt.print.Pageable;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ApiAdiqImpl implements ApiAdiqService {

    private static final String URL_BASE_HOMOLOG = "https://ecommerce-hml.adiq.io";
    private static final String URL_BASE_PROD = "https://ecommerce.adiq.io";
    private static final String CLIENT_CREDENTIALS = "client_credentials";
    private final PaymentRepository paymentRepository;

    @Override
    public int getAccessToken() {
        try {
           return   Request.Post(URL_BASE_HOMOLOG)
                    .useExpectContinue()
                    .version(HttpVersion.HTTP_1_1).
                    addHeader("Authorization", "Basic ZGV2ZWxvcGVycy5hZGlxLmlv")
                    .bodyString("grantType=" + CLIENT_CREDENTIALS, ContentType.DEFAULT_TEXT)
                    .execute().returnResponse().getStatusLine().getStatusCode();
        } catch (IOException e) {
            System.out.println("FALHAO AO BUSCAR TOKEN DE ACESSO: " +e.getMessage());
            return 0;
        }
    }
}

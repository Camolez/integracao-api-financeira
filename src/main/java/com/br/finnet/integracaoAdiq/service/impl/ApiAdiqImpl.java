package com.br.finnet.integracaoAdiq.service.impl;

import com.br.finnet.integracaoAdiq.domain.models.response.AdiqGetTokenModel;
import com.br.finnet.integracaoAdiq.service.ApiAdiqService;
import com.google.gson.Gson;
import org.apache.http.HttpVersion;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ApiAdiqImpl implements ApiAdiqService {

    public static final String URL_BASE_HOMOLOG = "https://ecommerce-hml.adiq.io";
    public static final String URL_BASE_PROD = "https://ecommerce.adiq.io";
    public static final String CLIENT_CREDENTIALS = "client_credentials";

    @Override
    public AdiqGetTokenModel getAccessToken() {
        try {
            String responseRequestToken =  Request.Post(URL_BASE_HOMOLOG)
                    .useExpectContinue()
                    .version(HttpVersion.HTTP_1_1).
                    addHeader("Authorization", "Basic ZGV2ZWxvcGVycy5hZGlxLmlv")
                    .bodyString("grantType=" + CLIENT_CREDENTIALS, ContentType.DEFAULT_TEXT)
                    .execute().returnContent().asString();
            JSONObject responseJson = new JSONObject(responseRequestToken);
            return new Gson().fromJson(String.valueOf(responseJson), AdiqGetTokenModel.class);
        } catch (IOException | JSONException e) {
            System.out.println("FALHAO AO BUSCAR TOKEN DE ACESSO: " +e.getMessage());
            return null;
        }
    }
}

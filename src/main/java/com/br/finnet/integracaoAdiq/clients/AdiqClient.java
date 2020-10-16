package com.br.finnet.integracaoAdiq.clients;

import com.br.finnet.integracaoAdiq.domain.models.request.PaymentRequestModel;
import com.br.finnet.integracaoAdiq.domain.models.request.TokenRequestModel;
import com.br.finnet.integracaoAdiq.domain.models.response.AdiqGetTokenResponse;
import com.br.finnet.integracaoAdiq.domain.models.response.AdiqPaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "${url.adiq}")
public interface AdiqClient {

    @PostMapping(value = "/auth/oauth2/v1/token", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<AdiqGetTokenResponse> getTokenAdiq(@RequestHeader("Authorization") String authorization,
                                                      TokenRequestModel tokenRequestModel);

    @PostMapping(value = "/v1/payments", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<AdiqPaymentResponse> requestPayment(@RequestHeader("Authorization") String authorization,
                                                       PaymentRequestModel paymentRequestModel);

}

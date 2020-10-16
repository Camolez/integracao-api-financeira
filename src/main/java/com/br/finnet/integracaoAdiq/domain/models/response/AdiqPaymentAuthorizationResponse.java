package com.br.finnet.integracaoAdiq.domain.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdiqPaymentAuthorizationResponse {
    private String returnCode;
    private String description;
    private String paymentId;
    private String authorizationCode;
    private String orderNumber;
    private String expireAt;
    private Integer amount;
    private String releaseAt;
}

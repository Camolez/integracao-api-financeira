package com.br.finnet.integracaoAdiq.domain.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdiqPaymentResponse {
    private AdiqPaymentAuthorizationResponse paymentAuthorization;
}

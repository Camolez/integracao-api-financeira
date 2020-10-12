package com.br.finnet.integracaoAdiq.service;

import com.br.finnet.integracaoAdiq.domain.models.request.PaymentModel;


public interface IntegracaoService {

   Integer solicitarPagamento (PaymentModel paymentModel);
}

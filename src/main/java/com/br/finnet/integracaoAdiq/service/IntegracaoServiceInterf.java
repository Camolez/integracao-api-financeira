package com.br.finnet.integracaoAdiq.service;

import com.br.finnet.integracaoAdiq.domain.models.PaymentModel;


public interface IntegracaoServiceInterf {

   Integer solicitarPagamento (PaymentModel paymentModel);
}

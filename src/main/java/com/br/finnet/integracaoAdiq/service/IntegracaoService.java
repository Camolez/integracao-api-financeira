package com.br.finnet.integracaoAdiq.service;

import com.br.finnet.integracaoAdiq.domain.models.request.PaymentModel;
import com.br.finnet.integracaoAdiq.domain.repositories.PaymentRepository;

import java.util.List;


public interface IntegracaoService {

   Integer solicitarPagamento (PaymentModel paymentModel);

   List<PaymentModel> findByFilter(String currencyCode, String captureType, String transactionType);

   void cancelPayment(Integer id);

}

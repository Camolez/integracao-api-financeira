package com.br.finnet.integracaoAdiq.service;

import com.br.finnet.integracaoAdiq.domain.enums.CaptureTypeEnum;
import com.br.finnet.integracaoAdiq.domain.enums.CurrencyEnum;
import com.br.finnet.integracaoAdiq.domain.enums.TransactionTypeEnum;
import com.br.finnet.integracaoAdiq.domain.models.request.PaymentModel;

import java.util.List;


public interface IntegracaoService {

   Integer solicitarPagamento (PaymentModel paymentModel);

   List<PaymentModel> findByFilter(CurrencyEnum currencyCode, CaptureTypeEnum captureType, TransactionTypeEnum transactionType);

   void cancelPayment(Integer id);

}

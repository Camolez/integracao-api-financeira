package com.br.finnet.integracaoAdiq.service;

import com.br.finnet.integracaoAdiq.domain.entities.PaymentEntity;
import com.br.finnet.integracaoAdiq.domain.enums.CaptureTypeEnum;
import com.br.finnet.integracaoAdiq.domain.enums.CurrencyEnum;
import com.br.finnet.integracaoAdiq.domain.enums.TransactionTypeEnum;
import com.br.finnet.integracaoAdiq.domain.models.request.PaymentRequestModel;

import java.util.List;


public interface IntegracaoService {

   Integer solicitarPagamento (PaymentRequestModel paymentModel);

   List<PaymentEntity> findByFilter(CurrencyEnum currencyCode, CaptureTypeEnum captureType, TransactionTypeEnum transactionType);

   void cancelPayment(Integer... integer);

}

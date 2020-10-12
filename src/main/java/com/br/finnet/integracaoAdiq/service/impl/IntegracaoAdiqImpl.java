package com.br.finnet.integracaoAdiq.service.impl;


import com.br.finnet.integracaoAdiq.domain.models.request.PaymentModel;
import com.br.finnet.integracaoAdiq.domain.repositories.PaymentRepository;
import com.br.finnet.integracaoAdiq.service.IntegracaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IntegracaoAdiqImpl implements IntegracaoService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public IntegracaoAdiqImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Integer solicitarPagamento(PaymentModel paymentModel) {
         paymentRepository.saveAndFlush(paymentModel);
         return paymentModel.getIdTransaction();
    }
}

package com.br.finnet.integracaoAdiq.service.impl;


import com.br.finnet.integracaoAdiq.domain.enums.CurrencyEnum;
import com.br.finnet.integracaoAdiq.domain.enums.TransactionTypeEnum;
import com.br.finnet.integracaoAdiq.domain.models.request.PaymentModel;
import com.br.finnet.integracaoAdiq.domain.repositories.PaymentRepository;
import com.br.finnet.integracaoAdiq.service.IntegracaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Optional;

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
         return paymentModel.getId();
    }

    @Override
    public List<PaymentModel> findByFilter(String currencyCode, String captureType, String transactionType) {
        return paymentRepository.findAll((Specification<PaymentModel>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate  = criteriaBuilder.conjunction();
            if(!currencyCode.isEmpty()){
                try{
                    CurrencyEnum.valueOf(currencyCode.toUpperCase());
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("currencyCode"),
                            CurrencyEnum.valueOf(currencyCode.toUpperCase())));
                }catch(IllegalArgumentException e){
                    return null;
                    //TODO VERIFICAR RETORNO
                }
            }
            if(!captureType.isEmpty()){
                predicate  = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("captureType"),
                        "%"+captureType+"%"));
            }
            if(!transactionType.isEmpty()){
                try{
                    TransactionTypeEnum.valueOf(transactionType.toUpperCase());
                    predicate  = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("transactionType"),
                            TransactionTypeEnum.valueOf(transactionType.toUpperCase())));
                }catch(IllegalArgumentException e){
                    return null;
                    //TODO VERIFICAR RETORNO
                }
            }
            return predicate;
        });
    }

    @Override
    public void cancelPayment(Integer id) {
        PaymentModel paymentModel = paymentRepository.findById(id).get();
        paymentModel.setStatusPayment("CANCELADO");
        paymentRepository.saveAndFlush(paymentModel);
    }

}

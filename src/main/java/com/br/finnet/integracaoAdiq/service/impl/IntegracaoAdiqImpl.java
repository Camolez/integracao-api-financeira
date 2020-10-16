package com.br.finnet.integracaoAdiq.service.impl;


import com.br.finnet.integracaoAdiq.clients.AdiqClient;
import com.br.finnet.integracaoAdiq.domain.entities.PaymentEntity;
import com.br.finnet.integracaoAdiq.domain.enums.CaptureTypeEnum;
import com.br.finnet.integracaoAdiq.domain.enums.CurrencyEnum;
import com.br.finnet.integracaoAdiq.domain.enums.TransactionTypeEnum;
import com.br.finnet.integracaoAdiq.domain.models.request.PaymentRequestModel;
import com.br.finnet.integracaoAdiq.domain.models.request.TokenRequestModel;
import com.br.finnet.integracaoAdiq.domain.models.response.AdiqGetTokenResponse;
import com.br.finnet.integracaoAdiq.domain.models.response.AdiqPaymentResponse;
import com.br.finnet.integracaoAdiq.domain.repositories.PaymentRepository;
import com.br.finnet.integracaoAdiq.service.IntegracaoService;
import feign.FeignException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.persistence.criteria.Predicate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class IntegracaoAdiqImpl implements IntegracaoService {

    private final PaymentRepository paymentRepository;
    private final AdiqClient adiqClient;

    public IntegracaoAdiqImpl(PaymentRepository paymentRepository, AdiqClient adiqClient) {
        this.paymentRepository = paymentRepository;
        this.adiqClient = adiqClient;
    }

    @Override
    public Integer solicitarPagamento(PaymentRequestModel paymentModel) {
        PaymentEntity paymentEntity = this.toPaymentEntity(paymentModel, this.doRequestPayment(paymentModel));
        paymentRepository.saveAndFlush(paymentEntity);
        return paymentEntity.getId();
    }

    @Override
    public List<PaymentEntity> findByFilter(CurrencyEnum currencyCode, CaptureTypeEnum captureType, TransactionTypeEnum transactionType) {
        return paymentRepository.findAll((Specification<PaymentEntity>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate  = criteriaBuilder.conjunction();
            if(Objects.nonNull(currencyCode)){
                  predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("currencyCode"),
                            currencyCode));
              }
            if(Objects.nonNull(captureType)){
                predicate  = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("captureType"),
                        captureType));
                }
            if(Objects.nonNull(transactionType)){
                  predicate  = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("transactionType"),
                            transactionType));

                 }
            return predicate;
        });
    }

    @Override
    public void cancelPayment(Integer... integer) {
        Integer id = Arrays.stream(integer).filter(Objects::nonNull).findFirst().orElseThrow(RuntimeException::new); //TODO CRIAR EXCEPTION id nao existente
        PaymentEntity paymentModel = paymentRepository.findById(id).orElseThrow(RuntimeException::new); //TODO CRIAR EXCEPTION
        paymentModel.setStatusPayment("CANCELADO"); //TODO CRIAR ENUM
        paymentRepository.saveAndFlush(paymentModel);
    }

    private PaymentEntity toPaymentEntity(PaymentRequestModel paymentRequestModel, AdiqPaymentResponse adiqPaymentResponse){
        return PaymentEntity
                .builder()
                .paymentId(adiqPaymentResponse.getPaymentAuthorization().getPaymentId())
                .authorizationCode(adiqPaymentResponse.getPaymentAuthorization().getAuthorizationCode())
                .orderNumber(adiqPaymentResponse.getPaymentAuthorization().getOrderNumber())
                .amount(adiqPaymentResponse.getPaymentAuthorization().getAmount())
                .transactionType(paymentRequestModel.getPayment().getTransactionType())
                .currencyCode(paymentRequestModel.getPayment().getCurrencyCode())
                .captureType(paymentRequestModel.getPayment().getCaptureType())
                .productType(paymentRequestModel.getPayment().getProductType())
                .documentNumber(paymentRequestModel.getCustomer().getDocumentNumber())
                .firstName(paymentRequestModel.getCustomer().getFirstName())
                .lastName(paymentRequestModel.getCustomer().getLastName())
                .city(paymentRequestModel.getCustomer().getCity())
                .state(paymentRequestModel.getCustomer().getState())
                .country(paymentRequestModel.getCustomer().getCountry())
                .build();
    }

    private String getToken(String grantType, String authorization){
        try {
            ResponseEntity<AdiqGetTokenResponse> tokenAdiq = adiqClient.getTokenAdiq(authorization, TokenRequestModel.builder().grantType(grantType).build());
            return tokenAdiq.getBody().getTokenType()+" "+tokenAdiq.getBody().getAccessToken();
        }catch (FeignException.BadRequest e){
           //TODO CRIAR EXCEPTION DE TOKE E TRATAR NO HANDLER
            throw new RuntimeException("");
        }
    }

    private AdiqPaymentResponse doRequestPayment(PaymentRequestModel paymentModel) {
        try {
            ResponseEntity<AdiqPaymentResponse> adiqPaymentResponse = adiqClient.requestPayment(this.getToken(paymentModel.getGrantType(), paymentModel.getAuthorization()), paymentModel);
            return adiqPaymentResponse.getBody();
        }catch (FeignException.BadRequest e){
            //TODO CRIAR EXCEPTION DE TOKE E TRATAR NO HANDLER
            throw new RuntimeException("");
        }
    }
}

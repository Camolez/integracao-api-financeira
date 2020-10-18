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
import com.br.finnet.integracaoAdiq.exceptions.FalhaPagamentoAdiqException;
import com.br.finnet.integracaoAdiq.exceptions.PagamentoNaoEncontradoException;
import com.br.finnet.integracaoAdiq.exceptions.TokenInvalidoException;
import com.br.finnet.integracaoAdiq.exceptions.ValorIdPagamentoVazioException;
import com.br.finnet.integracaoAdiq.service.IntegracaoService;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.persistence.criteria.Predicate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class IntegracaoAdiqImpl implements IntegracaoService {

    private static final String MSG_ID_NAO_ENCONTRADO = "ID PAGAMENTO NÃO ENCONTRADO";
    private static final String MSG_VALOR_ID_VAZIO = "FAVOR INFORMAR O ID DO PAGAMENTO QUE DEVE SER CANCELADO";
    private static final String STATUS_PAGAMENTO_CANCELADO = "CANCELADO";
    private static final String MSG_TOKEN_INVALIDO = "FAVOR INSERIR UM TOKEN VALIDO";
    private static final String FALHA_PAGAMENTO_ADIQ = "FALHA AO REALIZAR PAGAMENTO, FAVOR VERIFICAR AS INFORMAÇÕES ENVIADAS";
    private static final String CAMPO_CURRENCY_CODE = "currencyCode";
    private static final String CAMPO_CAPTURE_TYPE = "captureType";
    private static final String CAMPO_TRANSACTION_TYPE = "transactionType";
    private final PaymentRepository paymentRepository;
    private final AdiqClient adiqClient;

    @Override
    public Integer solicitarPagamento(PaymentRequestModel paymentModel) {
        final PaymentEntity paymentEntity = this.toPaymentEntity(paymentModel, this.doRequestPayment(paymentModel));
        paymentRepository.saveAndFlush(paymentEntity);
        return paymentEntity.getId();
    }
    @Override
    public List<PaymentEntity> findByFilter(CurrencyEnum currencyCode, CaptureTypeEnum captureType, TransactionTypeEnum transactionType) {
        return paymentRepository.findAll((Specification<PaymentEntity>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate  = criteriaBuilder.conjunction();
            if(Objects.nonNull(currencyCode)){
                  predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get(CAMPO_CURRENCY_CODE),
                            currencyCode.getCodCurrency()));
              }
            if(Objects.nonNull(captureType)){
                predicate  = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get(CAMPO_CAPTURE_TYPE),
                        captureType.getCaptureType()));
                }
            if(Objects.nonNull(transactionType)){
                  predicate  = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get(CAMPO_TRANSACTION_TYPE),
                            transactionType.getType()));
                 }
            return predicate;
        });
    }
    @Override
    public void cancelPayment(Integer... integer) {
        final Integer id = Arrays
                .stream(integer)
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(()->new ValorIdPagamentoVazioException(MSG_VALOR_ID_VAZIO));
       final PaymentEntity paymentModel = paymentRepository
                .findById(id)
                .orElseThrow(()->new PagamentoNaoEncontradoException(MSG_ID_NAO_ENCONTRADO));
        paymentModel.setStatusPayment(STATUS_PAGAMENTO_CANCELADO);
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
            throw new TokenInvalidoException(MSG_TOKEN_INVALIDO);
        }
    }

    private AdiqPaymentResponse doRequestPayment(PaymentRequestModel paymentModel) {
        try {
           final ResponseEntity<AdiqPaymentResponse> adiqPaymentResponse = adiqClient.requestPayment(this.getToken(paymentModel.getGrantType(), paymentModel.getAuthorization()), paymentModel);
            return adiqPaymentResponse.getBody();
        }catch (FeignException.BadRequest e){
            throw new FalhaPagamentoAdiqException(FALHA_PAGAMENTO_ADIQ);
        }
    }
}

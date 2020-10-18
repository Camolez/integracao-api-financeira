package com.br.finnet.integracaoAdiq.controller;

import com.br.finnet.integracaoAdiq.domain.entities.PaymentEntity;
import com.br.finnet.integracaoAdiq.domain.enums.CaptureTypeEnum;
import com.br.finnet.integracaoAdiq.domain.enums.CurrencyEnum;
import com.br.finnet.integracaoAdiq.domain.enums.TransactionTypeEnum;
import com.br.finnet.integracaoAdiq.domain.models.request.PaymentModel;
import com.br.finnet.integracaoAdiq.domain.models.request.PaymentRequestModel;
import com.br.finnet.integracaoAdiq.domain.repositories.PaymentRepository;
import com.br.finnet.integracaoAdiq.service.impl.IntegracaoAdiqImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/integracaoAdiq")
public class ControllerIntegracaoAdiq {

    private final IntegracaoAdiqImpl integracaoAdiq;
    private final PaymentRepository paymentRepository;

    public ControllerIntegracaoAdiq(IntegracaoAdiqImpl integracaoAdiq, PaymentRepository paymentRepository) {
        this.integracaoAdiq = integracaoAdiq;
        this.paymentRepository = paymentRepository;
    }

    @PostMapping(value = "/requestPayment")
    public ResponseEntity<PaymentModel> requestPayment(@RequestBody @Valid PaymentRequestModel paymentModel){
        Integer id = integracaoAdiq.solicitarPagamento(paymentModel);
        return ResponseEntity.created(ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("/{id}").
                buildAndExpand(id).
                toUri()).
                build();
    }
    @GetMapping(value = "/findAll")
    public ResponseEntity<List<PaymentEntity>> findAllPayments(){
        return ResponseEntity.ok(paymentRepository.findAll());
    }

    @GetMapping(value = "/findPayment")
    public ResponseEntity<List<PaymentEntity>> findPaymentByField(@RequestParam(required = false, name = "currencyCode") final CurrencyEnum currencyCode,
                                                                 @RequestParam(required = false, name = "captureType") final CaptureTypeEnum captureType,
                                                                 @RequestParam(required = false, name = "transactionType") final TransactionTypeEnum transactionType){
        return ResponseEntity.ok(integracaoAdiq.findByFilter(currencyCode,captureType, transactionType));
    }

    @PatchMapping(value = {"/cancelPayment/{idPath}","/cancelPayment"})
    public ResponseEntity<String> cancelPayment(@PathVariable(value = "idPath", required = false) Integer id,
                                                @RequestHeader(name = "idPayment", required = false) Integer idHeader,
                                                @RequestParam(name = "id", required = false) Integer idParam){
        integracaoAdiq.cancelPayment(id, idHeader, idParam);
        return ResponseEntity.ok("PAGAMENTO CANCELADO COM SUCESSO");
    }


}

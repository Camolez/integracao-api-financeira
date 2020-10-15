package com.br.finnet.integracaoAdiq.controller;

import com.br.finnet.integracaoAdiq.domain.models.request.PaymentModel;
import com.br.finnet.integracaoAdiq.domain.repositories.PaymentRepository;
import com.br.finnet.integracaoAdiq.service.impl.ApiAdiqImpl;
import com.br.finnet.integracaoAdiq.service.impl.IntegracaoAdiqImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/integracaoAdi")
public class ControllerIntegracaoAdiq {

    private final IntegracaoAdiqImpl integracaoAdiq;
    private final PaymentRepository paymentRepository;
    private final ApiAdiqImpl apiAdiq;

    @PostMapping(value = "/requestPayment")
    public ResponseEntity<PaymentModel> requestPayment(PaymentModel paymentModel){
        Integer id = integracaoAdiq.solicitarPagamento(paymentModel);
        return ResponseEntity.created(ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("/{id}").
                buildAndExpand(id).
                toUri()).
                build();
    }
    @GetMapping(value = "/findAll")
    public ResponseEntity<List<PaymentModel>> findAllPayments(){
        return ResponseEntity.ok(paymentRepository.findAll());
    }

    @GetMapping(value = "/findPayment")
    public ResponseEntity<List<PaymentModel>> findPaymentByField(@RequestParam(required = false, defaultValue = "") String currencyCode,
                                                                 @RequestParam(required = false, defaultValue = "") String captureType,
                                                                 @RequestParam(required = false, defaultValue = "") String transactionType){
        return ResponseEntity.ok(integracaoAdiq.findByFilter(currencyCode,captureType, transactionType));
    }

    @PatchMapping(value = "/cancelPayment/{id}")
    public ResponseEntity<String> cancelPayment(@PathVariable("id") Integer id ){
        integracaoAdiq.cancelPayment(id);
        return ResponseEntity.ok("PAGAMENTO CANCELADO COM SUCESSO");
    }


}

package com.br.finnet.integracaoAdiq.controller;

import com.br.finnet.integracaoAdiq.domain.models.request.PaymentModel;
import com.br.finnet.integracaoAdiq.domain.repositories.PaymentRepository;
import com.br.finnet.integracaoAdiq.service.impl.IntegracaoAdiqImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(value = "/integracaoAdiq/")
public class ControllerIntegracaoAdiq {

    private final IntegracaoAdiqImpl integracaoAdiq;
    private final PaymentRepository paymentRepository;


    @Autowired
    public ControllerIntegracaoAdiq(IntegracaoAdiqImpl integracaoAdiq, PaymentRepository paymentRepository) {
        this.integracaoAdiq = integracaoAdiq;
        this.paymentRepository = paymentRepository;
    }

    @PostMapping(value = "/requestPayment")
    public ResponseEntity<PaymentModel> requestPayment(PaymentModel paymentModel){
        Integer id = integracaoAdiq.solicitarPagamento(paymentModel);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri()).build();
    }
    @GetMapping(value = "/findAll")
    public ResponseEntity<List<PaymentModel>> findAllPayments(){
        return ResponseEntity.ok(paymentRepository.findAll());
    }
}

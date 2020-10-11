package com.br.finnet.integracaoAdiq.controller;

import com.br.finnet.integracaoAdiq.domain.models.PaymentModel;
import com.br.finnet.integracaoAdiq.service.impl.IntegracaoAdiqImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/integracaoAdiq/")
public class ControllerIntegracaoAdiq {

    private final IntegracaoAdiqImpl integracaoAdiq;

    @Autowired
    public ControllerIntegracaoAdiq(IntegracaoAdiqImpl integracaoAdiq) {
        this.integracaoAdiq = integracaoAdiq;
    }


    @PostMapping(value = "/solicitarPagamento")
    public ResponseEntity<PaymentModel> solicitarPagamento(PaymentModel paymentModel){
        Integer id = integracaoAdiq.solicitarPagamento(paymentModel);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri()).build();
    }
}

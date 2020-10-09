package com.br.finnet.integracaoAdiq.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping(value = "/integracaoAdiq/")
public class ControllerIntegracaoAdiq {

    @PostConstruct
    @GetMapping(value = "/a")
    public String teste(){
        return "testeApi";
    }
}

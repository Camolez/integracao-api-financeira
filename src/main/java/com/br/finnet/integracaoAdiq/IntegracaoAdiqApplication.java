package com.br.finnet.integracaoAdiq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class IntegracaoAdiqApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntegracaoAdiqApplication.class, args);
	}

}

package com.avalanches_pagamento.frameworksanddrivers.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.avalanches_pagamento")
public class AvalanchesPagamentoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AvalanchesPagamentoApplication.class, args);
	}

}

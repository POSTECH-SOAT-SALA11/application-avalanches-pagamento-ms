package com.avalanches_pagamento.stepdefinitions;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = TestConfig.class)  // Your configuration class
public class CucumberSpringConfiguration {
}


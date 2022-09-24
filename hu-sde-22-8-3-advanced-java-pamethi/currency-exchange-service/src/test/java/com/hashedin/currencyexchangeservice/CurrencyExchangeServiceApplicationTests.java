package com.hashedin.currencyexchangeservice;


import com.hashedin.currencyexchangeservice.models.CurrencyExchange;
import com.hashedin.currencyexchangeservice.services.ExchangeService;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {CurrencyExchangeServiceApplication.class}, loader = AnnotationConfigContextLoader.class)
public class CurrencyExchangeServiceApplicationTests {


    @Autowired
    ExchangeService exchangeService;



    @Before
    public void initialize(){
        insertOneTest();
    }

	@Test
	public void insertOneTest() {
        CurrencyExchange currencyExchange = new CurrencyExchange("dem",22.5);
        exchangeService.addOneCurrency(currencyExchange);
        CurrencyExchange currency = exchangeService.getOneCurrency("dem");
        Assertions.assertThat(currency.getValue()).isEqualTo(22.5);
    }

	@Test
	public void getAllTest() {
		List<CurrencyExchange> allCurrencies = exchangeService.getAllCurrencies();
		Assertions.assertThat(allCurrencies.size()).isGreaterThan(0);
	}
    	@Test
	public void getOneTest() {
		CurrencyExchange currency = exchangeService.getOneCurrency("dem");
		Assertions.assertThat(currency.getCurrency()).isEqualTo("dem");
	}


}
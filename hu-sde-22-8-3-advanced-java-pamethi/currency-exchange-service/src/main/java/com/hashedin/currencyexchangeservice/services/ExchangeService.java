package com.hashedin.currencyexchangeservice.services;

import com.hashedin.currencyexchangeservice.exceptionHandling.ResourceException;
import com.hashedin.currencyexchangeservice.models.CurrencyExchange;
import com.hashedin.currencyexchangeservice.repositories.ExchangeRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class ExchangeService {

	@Autowired
	private ExchangeRepository exchangeRepository;

	public static String cacheName = "allRates";

	@Autowired
	private CacheManager cacheManager;
	

	public CurrencyExchange getCurrencyByName(String currency) {
		return exchangeRepository.findByCurrency(currency)
				.orElseThrow(() -> new ResourceException("Currency not found with name " + currency));
	}
	
	@Cacheable(value = "currency-exchange-rates",key="#root.target.cacheName")
	public List<CurrencyExchange> getAllCurrencies() {
		return exchangeRepository.findAll();
	}
	
	@CacheEvict(value = "currency-exchange-rates",key="#root.target.cacheName")
	public CurrencyExchange addOneCurrency(CurrencyExchange currency) {
		return exchangeRepository.save(currency);
	}

	// Get one exchange rate
	@Cacheable(value = "currency-exchange-rates", key = "#currencyName")
	public CurrencyExchange getOneCurrency(String currencyName) {
		log.info(currencyName);
		return getCurrencyByName(currencyName);
	}

// Delete an exchange rate
	@CacheEvict(value = "currency-exchange-rates", key = "#currencyName")
	public ResponseEntity<Object> deleteOneCurrency(String currencyName) {
		CurrencyExchange exchange = getCurrencyByName(currencyName);
		exchangeRepository.delete(exchange);
		return ResponseEntity.ok().build();
	}

// update exchange
	@Caching(evict = { @CacheEvict(value = "currency-exchange-rates", key = "#currencyName"),
			@CacheEvict(value = "currency-exchange-rates", key = "#root.target.cacheName") })
	public CurrencyExchange updateOneCurrency(String currencyName, CurrencyExchange currency) {
		CurrencyExchange exchangeOptional = getCurrencyByName(currencyName);
		exchangeOptional.setCurrency(currency.getCurrency().toUpperCase());
		exchangeOptional.setValue(currency.getValue());
		exchangeRepository.save(exchangeOptional);
		return exchangeOptional;
	}

}

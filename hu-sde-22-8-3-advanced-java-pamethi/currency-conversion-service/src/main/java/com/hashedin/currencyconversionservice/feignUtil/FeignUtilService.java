package com.hashedin.currencyconversionservice.feignUtil;

import com.hashedin.currencyconversionservice.dto.CurrencyExchange;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(value = "currency-exchange-service",url = "http://localhost:8082")
public interface FeignUtilService {

    @GetMapping(path = "/health")
    String getHello();
    
    
//    @GetMapping(path = "/currency/get/{currencyName}")
//    Optional<CurrencyExchange> getOne(@PathVariable("currencyName") String currencyName);
    
    @GetMapping("/get/{currencyName}")
    Optional<CurrencyExchange> getOne(@PathVariable String currencyName);
  
}


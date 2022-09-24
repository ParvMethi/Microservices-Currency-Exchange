package com.hashedin.currencyconversionservice.controller;

import com.hashedin.currencyconversionservice.dto.CurrencyExchange;
import com.hashedin.currencyconversionservice.feignUtil.FeignUtilService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/exchange")
public class FeignExchangeController {
    private final Logger logger = LoggerFactory.getLogger(FeignExchangeController.class);

    @Autowired
    private FeignUtilService feignUtilService;

    @GetMapping("/hello")
    public String getHello(){
        logger.info("calling getHello to get the health of api");
        return feignUtilService.getHello();
    }

    @GetMapping("/get/{currencyName}")
    public Optional<CurrencyExchange> getRate(@PathVariable("currencyName") String currencyName){
        logger.info("calling getRate to get the rate of one currency");
        return feignUtilService.getOne(currencyName);
    }

    @GetMapping("/{fromAmount}/{fromCurrency}/to/{toCurrency}")
    public double getExchangedValue(@PathVariable("fromAmount") double fromAmount,@PathVariable("fromCurrency") String fromCurrency,@PathVariable("toCurrency") String toCurrency){
        logger.info("calling getExchangedValue to get the converted amount");
        Optional<CurrencyExchange> fromExchange = getRate(fromCurrency);
        Optional<CurrencyExchange> toExchange = getRate(toCurrency);
        if(!fromExchange.isPresent() | !toExchange.isPresent()) return 0.0;

        double fromRateToUsdBase = fromExchange.get().getValue();
        double toRateToUsdBase = toExchange.get().getValue();
        return (toRateToUsdBase / fromRateToUsdBase)* fromAmount;
    }

}
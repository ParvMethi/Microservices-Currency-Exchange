package com.hashedin.currencyconversionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class CurrencyExchange {
    private int id;
    private String currency;
    private double value;

}
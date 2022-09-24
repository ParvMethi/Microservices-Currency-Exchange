package com.hashedin.currencyexchangeservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "EXCHANGE_RATE")
@Data @NoArgsConstructor @AllArgsConstructor
public class CurrencyExchange {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "currency", nullable = false)
    private String currency;
    @Column(name = "value", nullable = false)
    private double value;
    

    public CurrencyExchange(String currency, double value) {
        this.currency = currency;
        this.value = value;
    }
    
    
}
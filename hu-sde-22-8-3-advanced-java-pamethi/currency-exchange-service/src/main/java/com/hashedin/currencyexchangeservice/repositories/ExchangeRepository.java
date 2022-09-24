package com.hashedin.currencyexchangeservice.repositories;

import com.hashedin.currencyexchangeservice.models.CurrencyExchange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExchangeRepository extends JpaRepository<CurrencyExchange, Integer> {
    Optional<CurrencyExchange> findByCurrency(String currency);

}

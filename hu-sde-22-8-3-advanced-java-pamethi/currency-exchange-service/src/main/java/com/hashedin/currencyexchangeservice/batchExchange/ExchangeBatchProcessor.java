package com.hashedin.currencyexchangeservice.batchExchange;

import com.hashedin.currencyexchangeservice.models.CurrencyExchange;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class ExchangeBatchProcessor implements ItemProcessor<CurrencyExchange, CurrencyExchange> {

    private static final Logger log = LoggerFactory.getLogger(ExchangeBatchProcessor.class);

    @Override
    public CurrencyExchange process(final CurrencyExchange currencyExchange) throws Exception {
        log.info("processing user data.....{}", currencyExchange);
        return currencyExchange;
    }
}

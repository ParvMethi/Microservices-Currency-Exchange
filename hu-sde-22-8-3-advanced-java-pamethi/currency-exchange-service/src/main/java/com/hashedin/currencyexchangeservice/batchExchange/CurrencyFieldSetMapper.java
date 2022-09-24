package com.hashedin.currencyexchangeservice.batchExchange;

import com.hashedin.currencyexchangeservice.models.CurrencyExchange;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class CurrencyFieldSetMapper implements FieldSetMapper<CurrencyExchange> {

    @Override
    public CurrencyExchange mapFieldSet(FieldSet fieldSet) {
        return new CurrencyExchange(fieldSet.readInt("id"),
                    fieldSet.readString("currency").toUpperCase(),
                fieldSet.readDouble("value"));
    }
}
package com.hashedin.currencyexchangeservice.config;


import com.hashedin.currencyexchangeservice.batchExchange.CurrencyFieldSetMapper;
import com.hashedin.currencyexchangeservice.batchExchange.ExchangeBatchListener;
import com.hashedin.currencyexchangeservice.batchExchange.ExchangeBatchProcessor;
import com.hashedin.currencyexchangeservice.models.CurrencyExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    @Autowired
    public DataSource dataSource;
    private static final Logger log = LoggerFactory.getLogger(ExchangeBatchListener.class);

    @Bean
    public FlatFileItemReader<CurrencyExchange> fileItemReader() {
        FlatFileItemReader<CurrencyExchange> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("batchCurrency.csv"));
        reader.setLinesToSkip(1);
        reader.setLineMapper(new DefaultLineMapper<CurrencyExchange>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("id","currency", "value");
            }});
            setFieldSetMapper(new CurrencyFieldSetMapper());
        }});
        return reader;
    }

    @Bean
    public ExchangeBatchProcessor processor() {
        return new ExchangeBatchProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<CurrencyExchange> writer() {
        JdbcBatchItemWriter<CurrencyExchange> itemWriter = new JdbcBatchItemWriter<>();
        itemWriter.setDataSource(dataSource);
        itemWriter.setSql("INSERT INTO exchange_rate (id,currency,value) VALUES (:id,:currency, :value)");
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        itemWriter.afterPropertiesSet();
        return itemWriter;
    }

    @Bean
    public Job importUserJob(ExchangeBatchListener listener) {
        return jobBuilderFactory.get("import-currency-Job").incrementer(new RunIdIncrementer()).listener(listener).flow(step()).end().build();
    }

    @Bean
    public Step step() {
        return stepBuilderFactory.get("step").<CurrencyExchange, CurrencyExchange>chunk(10).reader(fileItemReader()).processor(processor()).writer(writer()).build();
    }

}


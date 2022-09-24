package com.hashedin.currencyexchangeservice.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.batch.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.hashedin.currencyexchangeservice.models.CurrencyExchange;
import com.hashedin.currencyexchangeservice.services.ExchangeService;



@RestController
public class ExchangeController {
	
	@Autowired
	private ExchangeService exchangeService;
	
	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
    private Job importUserJob;
	
    private static final Logger log = LoggerFactory.getLogger(ExchangeController.class);

	
	@GetMapping("/parv")
	public String heel(){
		return "hello";
	}
	
	// Get all available exchange rates through an authenticated request
    @GetMapping("/all")
    public List<CurrencyExchange> getAllCurrencies() {
        return exchangeService.getAllCurrencies();
    }

    // Add an exchange rate as an ADMIN
    @PostMapping("/add")
    public CurrencyExchange addOne(@RequestBody CurrencyExchange currency) {

//    	System.out.println(currency.getValue());

        return exchangeService.addOneCurrency(currency);
    }

    // Get one exchange rate through an authenticated request
    @GetMapping("/get/{currencyName}")
    public CurrencyExchange getOne(@PathVariable String currencyName) {
        return exchangeService.getOneCurrency(currencyName.toUpperCase());
    }

    // Delete an exchange rate as an ADMIN
    @DeleteMapping("/delete/{currencyName}")
    public ResponseEntity<Object> deleteOne(@PathVariable String currencyName) {
        return exchangeService.deleteOneCurrency(currencyName);
    }

    // Update an exchange rate as an ADMIN
    @PutMapping("/update/{currencyName}")
    public CurrencyExchange updateOne(@RequestBody CurrencyExchange currency, @PathVariable String currencyName) {
        return exchangeService.updateOneCurrency(currencyName, currency);
    }
    
 // batch processing\
    @GetMapping("/batchUpload")
    public BatchStatus load() throws Exception {

        JobParameters jobParameter = new JobParametersBuilder()
                .addString("JobId",UUID.randomUUID().toString())
                .addLong("time",System.currentTimeMillis()).toJobParameters();

        JobExecution execution = jobLauncher.run(importUserJob, jobParameter);
        log.info("STATUS :: "+execution.getStatus());

        log.info("Batch is Running...");
        while (execution.isRunning()) {
            log.info("...");
        }

        return execution.getStatus();

    }
	
	
}

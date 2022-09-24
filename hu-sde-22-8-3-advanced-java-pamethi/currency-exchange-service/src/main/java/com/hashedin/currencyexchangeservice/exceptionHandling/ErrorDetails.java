package com.hashedin.currencyexchangeservice.exceptionHandling;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class ErrorDetails {
    private String message;
	private String details;

}

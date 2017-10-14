package com.spring.hateoas.wrapper;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.spring.hateoas.domain.Customer;

public class CustomerResource extends ResourceSupport {

	@JsonUnwrapped
	private Customer customer;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}

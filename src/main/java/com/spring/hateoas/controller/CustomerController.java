package com.spring.hateoas.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spring.hateoas.domain.Customer;
import com.spring.hateoas.domain.Invoice;
import com.spring.hateoas.service.CustomerService;
import com.spring.hateoas.wrapper.CustomerResource;


@RestController
@RequestMapping(value = "/api/customer", produces = "application/hal+json")
//@ExposesResourceFor(Customer.class)
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    EntityLinks entityLinks;

    @RequestMapping(method = RequestMethod.GET)
    public Resources<Resource<CustomerResource>> getCustomers() {

        return customerToResource(customerService.getCustomers());

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Resource<CustomerResource> getCustomer(@PathVariable int id) {
        return customerToResource(customerService.getCustomer(id));

    }

    private Resources<Resource<CustomerResource>> customerToResource(List<Customer> customers) {

        Link selfLink = linkTo(methodOn(CustomerController.class).getCustomers()).withSelfRel();

        List<Resource<CustomerResource>> customerResources = customers.stream().map(customer -> customerToResource(customer)).collect(Collectors.toList());

        return new Resources<>(customerResources, selfLink);

    }

    private Resource<CustomerResource> customerToResource(Customer customer) {
        Link selfLink   = linkTo(methodOn(CustomerController.class).getCustomer(customer.getId())).withSelfRel();

        CustomerResource customerResource = new CustomerResource();
        customerResource.setCustomer(customer);
        customerResource.add(selfLink);
        
        Link allInvoiceLink = entityLinks.linkToCollectionResource(Invoice.class).withRel("all-invoices");
        Link invoiceLink = linkTo(methodOn(InvoiceController.class).getInvoiceByCustomerId(customer.getId())).withRel("invoice");

        return new Resource<>(customerResource, selfLink,  invoiceLink, allInvoiceLink);

    }
}
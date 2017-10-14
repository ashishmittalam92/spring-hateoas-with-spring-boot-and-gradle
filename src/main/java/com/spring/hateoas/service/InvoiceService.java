package com.spring.hateoas.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.spring.hateoas.domain.Invoice;

@Service
public class InvoiceService {
    List<Invoice> invoiceList;

    public InvoiceService() {
        invoiceList = new LinkedList<>();
        invoiceList.add(new Invoice(1, 50, "Invoice 1 ", 1));
        invoiceList.add(new Invoice(2, 100, "Invoice 2 ", 1));
        invoiceList.add(new Invoice(3, 34, "Invoice 3 ", 2));
        invoiceList.add(new Invoice(4, 44, "Invoice 4 ", 2));
    }

    public List<Invoice> getInvoiceByCustomerId(int customerId) {
        return invoiceList.stream().filter(invoice1 -> invoice1.getCustomerId() == customerId).collect(Collectors.toList());
    }

    public Invoice getInvoiceById(int id) {
        return invoiceList.stream().filter(invoice1 -> invoice1.getId() == id).findFirst().get();
    }

    public List<Invoice> findAll() {
        return new ArrayList<>(invoiceList);
    }
}
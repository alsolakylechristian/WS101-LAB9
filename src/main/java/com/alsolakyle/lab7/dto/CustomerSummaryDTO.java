package com.alsolakyle.lab7.dto;

import lombok.Value;

@Value
public class CustomerSummaryDTO {

    private Long id;
    private String name;
    private String email;
    // Omit the List<Invoice> invoices field, which breaks the cycle
}
package com.alsolakyle.lab7.dto;

import lombok.Value;

@Value
public class ProductSummaryDTO {

    private Long id;
    private String name;
    private Double price;
}
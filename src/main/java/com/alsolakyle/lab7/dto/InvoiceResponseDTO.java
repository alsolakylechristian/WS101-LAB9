package com.alsolakyle.lab7.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Value; // Use @Value for immutable DTOs

@Value // Lombok annotation for immutable class (Getters, AllArgsConstructor)
public class InvoiceResponseDTO {

    private Long id;
    private LocalDate invoiceDate;

    // Instead of the full Customer entity, use a summary DTO
    private CustomerSummaryDTO customer;

    // Instead of the full Product list, use a simplified list if needed,
    // or keep the full List<Product> if Product has no circular reference.
    // Assuming Product is simple, we will use a list of simplified Product DTOs.
    private List<ProductSummaryDTO> products;

    // You could also add a total amount field here
    // private Double totalAmount;
}
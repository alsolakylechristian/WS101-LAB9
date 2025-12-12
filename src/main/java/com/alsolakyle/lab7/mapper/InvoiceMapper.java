package com.alsolakyle.lab7.mapper;

import com.alsolakyle.lab7.dto.CustomerSummaryDTO;
import com.alsolakyle.lab7.dto.InvoiceResponseDTO;
import com.alsolakyle.lab7.dto.ProductSummaryDTO;
import com.alsolakyle.lab7.model.Invoice;
import com.alsolakyle.lab7.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InvoiceMapper {

    public InvoiceResponseDTO toDTO(Invoice invoice) {
        if (invoice == null) {
            return null;
        }

        // Map the related Customer entity to its DTO summary
        CustomerSummaryDTO customerDTO = new CustomerSummaryDTO(
                invoice.getCustomer().getId(),
                invoice.getCustomer().getName(),
                invoice.getCustomer().getEmail()
        );

        // Map the related Product entities to their DTO summaries
        List<ProductSummaryDTO> productDTOs = invoice.getProducts().stream()
                .map(this::toProductDTO)
                .collect(Collectors.toList());

        // Create and return the final Invoice Response DTO
        return new InvoiceResponseDTO(
                invoice.getId(),
                invoice.getInvoiceDate(),
                customerDTO,
                productDTOs
        );
    }

    private ProductSummaryDTO toProductDTO(Product product) {
        return new ProductSummaryDTO(
                product.getId(),
                product.getName(),
                product.getPrice()
        );
    }
}
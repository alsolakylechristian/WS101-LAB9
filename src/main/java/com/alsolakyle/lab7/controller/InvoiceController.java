package com.alsolakyle.lab7.controller;

import com.alsolakyle.lab7.model.Invoice;
import com.alsolakyle.lab7.service.InvoiceService;
import com.alsolakyle.lab7.dto.InvoiceResponseDTO; // New import
import com.alsolakyle.lab7.mapper.InvoiceMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final InvoiceMapper invoiceMapper;

    public InvoiceController(InvoiceService invoiceService, InvoiceMapper invoiceMapper) {
        this.invoiceService = invoiceService;
        this.invoiceMapper = invoiceMapper; // Initialize the mapper
    }

    // 1. READ ALL - GET /api/invoices
    @GetMapping
    // Change return type to DTO
    public ResponseEntity<List<InvoiceResponseDTO>> getAllInvoices() {
        List<InvoiceResponseDTO> dtoList = invoiceService.findAll().stream()
                .map(invoiceMapper::toDTO) // Map each entity to its DTO
                .collect(Collectors.toList());

        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    // 2. READ ONE - GET /api/invoices/{id}
    @GetMapping("/{id}")
    // Change return type to DTO
    public ResponseEntity<InvoiceResponseDTO> getInvoiceById(@PathVariable Long id) {
        return invoiceService.findById(id)
                // Map the fetched Invoice to its DTO before returning
                .map(invoiceMapper::toDTO)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 3. CREATE - POST /api/invoices
    @PostMapping
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice) {
        return invoiceService.createInvoice(invoice)
                .map(createdInvoice -> new ResponseEntity<>(createdInvoice, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    // 4. DELETE - DELETE /api/invoices/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        boolean deleted = invoiceService.delete(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
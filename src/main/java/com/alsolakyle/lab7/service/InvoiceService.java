package com.alsolakyle.lab7.service;

import com.alsolakyle.lab7.model.Customer;
import com.alsolakyle.lab7.model.Invoice;
import com.alsolakyle.lab7.model.Product;
import com.alsolakyle.lab7.repository.CustomerRepository;
import com.alsolakyle.lab7.repository.InvoiceRepository;
import com.alsolakyle.lab7.repository.ProductRepository;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public InvoiceService(InvoiceRepository invoiceRepository, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.invoiceRepository = invoiceRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    public List<Invoice> findAll() {
        return invoiceRepository.findAll();
    }

    public Optional<Invoice> findById(Long id) {
        return invoiceRepository.findById(id);
    }

    @Transactional
    public Optional<Invoice> createInvoice(Invoice invoice) {
        // 1. Load the managed Customer entity
        Optional<Customer> customerOpt = customerRepository.findById(invoice.getCustomer().getId());
        if (customerOpt.isEmpty()) {
            return Optional.empty();
        }

        // 2. Load the managed Product entities
        List<Long> productIds = invoice.getProducts().stream()
                .map(Product::getId)
                .collect(Collectors.toList());

        List<Product> managedProducts = productRepository.findAllById(productIds);

        // Check if all requested products were found
        if (managedProducts.size() != productIds.size()) {
            return Optional.empty();
        }

        // 3. Set the managed entities and save
        invoice.setCustomer(customerOpt.get());
        invoice.setProducts(managedProducts);
        invoice.setId(null); // Ensure it's a new entity

        return Optional.of(invoiceRepository.save(invoice));
    }

    public boolean delete(Long id) {
        if (invoiceRepository.existsById(id)) {
            invoiceRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // TRUNCATE FEATURE
    @Transactional
    public void truncateTable() {
        invoiceRepository.truncateTable();
    }
}
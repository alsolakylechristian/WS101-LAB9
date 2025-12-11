package com.alsolakyle.lab7.controller;

import com.alsolakyle.lab7.model.Customer;
import com.alsolakyle.lab7.model.Invoice;
import com.alsolakyle.lab7.model.Product;
import com.alsolakyle.lab7.service.CustomerService;
import com.alsolakyle.lab7.service.InvoiceService;
import com.alsolakyle.lab7.service.ProductService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class SalesGraphQLController {

    private final CustomerService customerService;
    private final InvoiceService invoiceService;
    private final ProductService productService;

    public SalesGraphQLController(
            CustomerService customerService,
            InvoiceService invoiceService,
            ProductService productService) {
        this.customerService = customerService;
        this.invoiceService = invoiceService;
        this.productService = productService;
    }

    // =========================================================================
    // PRODUCT QUERIES & MUTATIONS
    // =========================================================================
    @QueryMapping
    public List<Product> allProducts() {
        return productService.findAll();
    }

    @QueryMapping
    public Optional<Product> productById(@Argument Long id) {
        return productService.findById(id);
    }

    @MutationMapping
    public Product createProduct(@Argument String name, @Argument Double price) {
        Product newProduct = new Product();
        newProduct.setName(name);
        newProduct.setPrice(price);
        return productService.save(newProduct);
    }

    @MutationMapping
    public Product updateProduct(@Argument Long id, @Argument String name, @Argument Double price) {
        Product productDetails = new Product();
        productDetails.setName(name);
        productDetails.setPrice(price);

        Optional<Product> updated = productService.update(id, productDetails);
        return updated.orElse(null);
    }

    @MutationMapping
    public Boolean deleteProduct(@Argument Long id) {
        return productService.delete(id);
    }

    // =========================================================================
    // FULL TRUNCATE FEATURE (for GraphQL - FIXED)
    // =========================================================================
    @MutationMapping
    public Boolean resetAllData() {
        try {
            // Call the master transactional method in the ProductService
            productService.resetDatabase();
            return true;
        } catch (Exception e) {
            // Log error here if needed
            return false;
        }
    }


    // =========================================================================
    // CUSTOMER QUERIES & MUTATIONS
    // =========================================================================
    @QueryMapping
    public List<Customer> allCustomers() {
        return customerService.findAll();
    }

    @QueryMapping
    public Optional<Customer> customerById(@Argument Long id) {
        return customerService.findById(id);
    }

    @MutationMapping
    public Customer createCustomer(@Argument String name, @Argument String email) {
        Customer newCustomer = new Customer();
        newCustomer.setName(name);
        newCustomer.setEmail(email);
        return customerService.save(newCustomer);
    }

    @MutationMapping
    public Customer updateCustomer(@Argument Long id, @Argument String name, @Argument String email) {
        Customer customerDetails = new Customer();
        customerDetails.setName(name);
        customerDetails.setEmail(email);

        Optional<Customer> updated = customerService.update(id, customerDetails);
        return updated.orElse(null);
    }

    @MutationMapping
    public Boolean deleteCustomer(@Argument Long id) {
        return customerService.delete(id);
    }


    // =========================================================================
    // INVOICE QUERIES & MUTATIONS
    // =========================================================================
    @QueryMapping
    public List<Invoice> allInvoices() {
        return invoiceService.findAll();
    }

    @QueryMapping
    public Optional<Invoice> invoiceById(@Argument Long id) {
        return invoiceService.findById(id);
    }

    @MutationMapping
    public Invoice createInvoice(@Argument Long customerId, @Argument List<Long> productIds) {
        Invoice newInvoice = new Invoice();
        newInvoice.setInvoiceDate(LocalDate.now());

        // Prepare transient entities with only IDs set for service layer processing
        Customer transientCustomer = new Customer();
        transientCustomer.setId(customerId);
        newInvoice.setCustomer(transientCustomer);

        List<Product> transientProducts = productIds.stream()
                .map(id -> {
                    Product p = new Product();
                    p.setId(id);
                    return p;
                })
                .collect(Collectors.toList());
        newInvoice.setProducts(transientProducts);

        return invoiceService.createInvoice(newInvoice).orElse(null);
    }
}
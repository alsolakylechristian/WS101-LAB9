package com.alsolakyle.lab7.service;

import com.alsolakyle.lab7.model.Product;
import com.alsolakyle.lab7.repository.ProductRepository;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final EntityManager entityManager;
    private final CustomerService customerService;
    private final InvoiceService invoiceService;

    // CONSTRUCTOR: Inject all required dependencies
    public ProductService(ProductRepository productRepository, EntityManager entityManager,
                          CustomerService customerService, InvoiceService invoiceService) {
        this.productRepository = productRepository;
        this.entityManager = entityManager;
        this.customerService = customerService;
        this.invoiceService = invoiceService;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public boolean delete(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Product> update(Long id, Product updatedDetails) {
        return productRepository.findById(id)
                .map(product -> {
                    if (updatedDetails.getName() != null) {
                        product.setName(updatedDetails.getName());
                    }
                    if (updatedDetails.getPrice() != null) {
                        product.setPrice(updatedDetails.getPrice());
                    }
                    return productRepository.save(product);
                });
    }

    // TRUNCATE FEATURE (used internally by resetDatabase)
    @Transactional
    public void truncateTable() {
        productRepository.truncateTable();
    }

    // MASTER RESET METHOD: Executes all steps on the SAME database connection
    @Transactional
    public void resetDatabase() {
        try {
            // STEP 1: Disable foreign key checks on the current connection
            entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();

            // STEP 2: Execute all truncates (Order is generally dependent-first: Invoice -> Customer -> Product)
            invoiceService.truncateTable();
            customerService.truncateTable();
            this.truncateTable();

        } finally {
            // STEP 3: Re-enable foreign key checks on the same connection
            entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
        }
    }
}
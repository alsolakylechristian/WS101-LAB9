package com.alsolakyle.lab7.service;

import com.alsolakyle.lab7.model.Product;
import com.alsolakyle.lab7.repository.ProductRepository; // NEW IMPORT
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository; // Use Repository instead of List

    // Constructor Injection
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll(); // Delegate to JPA
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id); // Delegate to JPA
    }

    public Product save(Product product) {
        // JPA handles ID generation and persistence automatically
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
                    // Update the managed entity
                    product.setName(updatedDetails.getName());
                    product.setPrice(updatedDetails.getPrice());
                    return productRepository.save(product); // Explicit save (optional but safe)
                });
    }

    // 6. NEW: Method to call the TRUNCATE functionality
    public void resetProductIds() {
        productRepository.resetAutoIncrement();
    }
}
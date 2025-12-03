package com.alsolakyle.lab7.service;

import com.alsolakyle.lab7.model.Product;
import com.alsolakyle.lab7.repository.ProductRepository; // New import
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository; // Use Repository instead of List

    // Constructor Injection of the Repository
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll(); // Simple delegation
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id); // Simple delegation
    }

    public Product save(Product product) {
        // Repository handles INSERT vs. UPDATE and ID generation automatically [cite: 46, 72]
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
                    // Update the managed entity [cite: 88]
                    product.setName(updatedDetails.getName());
                    product.setPrice(updatedDetails.getPrice());
                    // JPA's dirty checking will handle the update on transaction commit[cite: 89].
                    // Explicit save() is optional for managed entities but safe.
                    return productRepository.save(product);
                });
    }
}
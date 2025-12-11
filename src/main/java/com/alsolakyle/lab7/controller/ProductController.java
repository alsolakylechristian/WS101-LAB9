package com.alsolakyle.lab7.controller;

import com.alsolakyle.lab7.service.ProductService;
import com.alsolakyle.lab7.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products") // Base path
public class ProductController {

    private final ProductService productService;

    // Constructor Injection (Only injects what is needed for this controller)
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 1. READ ALL - Return 200 OK
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    // 2. READ ONE - Return 200 OK or 404 Not Found
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.findById(id)
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 3. CREATE - Return 201 Created
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.save(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    // 4. UPDATE - Return 200 OK or 404 Not Found
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        return productService.update(id, productDetails)
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 5. DELETE - Return 204 No Content or 404 Not Found
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        boolean deleted = productService.delete(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 6. TRUNCATE ALL DATA FEATURE (FINAL FIX)
    @DeleteMapping("/admin/reset")
    public ResponseEntity<String> resetAllData() {
        try {
            // Call the master transactional method in the ProductService
            productService.resetDatabase();

            return new ResponseEntity<>("All tables (Invoice, Customer, Product) truncated and auto-increment reset.", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to truncate tables due to: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
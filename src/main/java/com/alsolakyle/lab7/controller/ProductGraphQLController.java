package com.alsolakyle.lab7.controller;

import com.alsolakyle.lab7.model.Product;
import com.alsolakyle.lab7.service.ProductService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller // Registers this as a GraphQL handler
public class ProductGraphQLController {

    private final ProductService productService;

    public ProductGraphQLController(ProductService productService) {
        this.productService = productService;
    }

    // --- QUERIES ---

    // Matches 'allProducts' in schema.graphqls
    @QueryMapping
    public List<Product> allProducts() {
        return productService.findAll();
    }

    // Matches 'productById' in schema.graphqls
    @QueryMapping
    public Optional<Product> productById(@Argument Long id) {
        return productService.findById(id);
    }

    // --- MUTATIONS ---

    // Matches 'createProduct' in schema.graphqls
    @MutationMapping
    public Product createProduct(@Argument String name, @Argument Double price) {
        // ID is null because JPA handles auto-increment logic
        Product newProduct = new Product(null, name, price);
        return productService.save(newProduct);
    }

    // Matches 'updateProduct' in schema.graphqls
    @MutationMapping
    public Product updateProduct(@Argument Long id, @Argument String name, @Argument Double price) {
        Product productDetails = new Product(null, name, price);
        Optional<Product> updated = productService.update(id, productDetails);
        return updated.orElse(null);
    }

    // Matches 'deleteProduct' in schema.graphqls
    @MutationMapping
    public Boolean deleteProduct(@Argument Long id) {
        return productService.delete(id);
    }

    // 6. NEW: Mutation to reset all data and ID counter
    @MutationMapping
    public Boolean resetAllData() {
        productService.resetProductIds();
        return true;
    }
}
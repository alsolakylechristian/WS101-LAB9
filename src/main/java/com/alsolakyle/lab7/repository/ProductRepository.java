package com.alsolakyle.lab7.repository;

import com.alsolakyle.lab7.model.Product;
import org.springframework.data.jpa.repository.JpaRepository; // Import for JPA [cite: 110]

// JpaRepository<EntityClass, PrimaryKeyType> [cite: 113]
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Spring Data automatically implements basic CRUD methods: save(), findById(), findAll(), deleteById(), etc. [cite: 59, 108]
    // You can also add Derived Query Methods here (e.g., List<Product> findByName(String name);) [cite: 93]
}
package com.alsolakyle.lab7.repository;

import com.alsolakyle.lab7.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import jakarta.transaction.Transactional; // Use jakarta for Spring Boot 3+

// Extends JpaRepository<EntityClass, PrimaryKeyType>
// This interface provides all standard CRUD methods automatically.
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Executes the TRUNCATE TABLE command.
     * This deletes all records and resets the auto-increment ID counter back to 1.
     * Use this for integration testing or administrative resets.
     */
    @Modifying // Required for queries that modify data (like TRUNCATE)
    @Transactional // Required to execute DML/DDL operations
    @Query(value = "TRUNCATE TABLE product", nativeQuery = true)
    void resetAutoIncrement();
}
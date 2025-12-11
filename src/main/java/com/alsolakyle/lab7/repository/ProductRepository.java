package com.alsolakyle.lab7.repository;

import com.alsolakyle.lab7.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import jakarta.transaction.Transactional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    /*
     * Executes the TRUNCATE TABLE command for the 'product' table.
     * This deletes all records and resets the auto-increment ID counter.
     */
    @Modifying // Required for DML/DDL operations
    @Transactional // Ensures the operation runs within a transaction
    @Query(value = "TRUNCATE TABLE product", nativeQuery = true)
    void truncateTable();
}
package com.alsolakyle.lab7.repository;

import com.alsolakyle.lab7.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import jakarta.transaction.Transactional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /*
     * Executes the TRUNCATE TABLE command for the 'customer' table.
     */
    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE customer", nativeQuery = true)
    void truncateTable();
}
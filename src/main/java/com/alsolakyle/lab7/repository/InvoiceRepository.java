package com.alsolakyle.lab7.repository;

import com.alsolakyle.lab7.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import jakarta.transaction.Transactional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    /*
     * Executes the TRUNCATE TABLE command for the 'invoice' table.
     */
    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE invoice", nativeQuery = true)
    void truncateTable();
}
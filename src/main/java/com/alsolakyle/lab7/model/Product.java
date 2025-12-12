package com.alsolakyle.lab7.model;

import com.fasterxml.jackson.annotation.JsonIgnore; // NEW IMPORT
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString; // NEW IMPORT
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString(exclude = "invoices") // ADDED: Prevents recursion in toString()
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double price;

    // 2. Invoice <-> Product (Many-to-Many - Inverse Side)
    @ManyToMany(mappedBy = "products", fetch = FetchType.LAZY)
    @JsonIgnore // ADDED: Breaks the Product -> Invoices -> Product loop
    private List<Invoice> invoices;
}
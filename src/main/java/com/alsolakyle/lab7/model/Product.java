package com.alsolakyle.lab7.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double price;

    // 2. Invoice <-> Product (Many-to-Many - Inverse Side)
    @ManyToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private List<Invoice> invoices;
}
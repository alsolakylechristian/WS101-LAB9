package com.alsolakyle.lab7.model;

import jakarta.persistence.Entity; // Import for JPA [cite: 97, 123]
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity //  This marks it as a database table [cite: 98, 124]
public class Product {
    @Id // Marks the primary key [cite: 100, 132]
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Uses database's auto-increment [cite: 101, 136]
    private Long id;
    private String name;
    private Double price;
}
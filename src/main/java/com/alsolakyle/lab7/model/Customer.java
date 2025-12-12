package com.alsolakyle.lab7.model;

import com.fasterxml.jackson.annotation.JsonManagedReference; // NEW IMPORT
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;

    // 1. Customer <-> Invoice (One-to-Many)
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // ADDED: Serializes the list of Invoices
    private List<Invoice> invoices;
}
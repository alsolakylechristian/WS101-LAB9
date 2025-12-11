package com.alsolakyle.lab7.service;

import com.alsolakyle.lab7.model.Customer;
import com.alsolakyle.lab7.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public Optional<Customer> update(Long id, Customer updatedDetails) {
        return customerRepository.findById(id)
                .map(customer -> {
                    if (updatedDetails.getName() != null) {
                        customer.setName(updatedDetails.getName());
                    }
                    if (updatedDetails.getEmail() != null) {
                        customer.setEmail(updatedDetails.getEmail());
                    }
                    return customerRepository.save(customer);
                });
    }

    public boolean delete(Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // TRUNCATE FEATURE
    @Transactional
    public void truncateTable() {
        customerRepository.truncateTable();
    }
}